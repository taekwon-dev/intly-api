package com.youn.intly.configuration;

import com.youn.intly.authentication.application.AuthService;
import com.youn.intly.authentication.presentation.AuthenticationPrincipalArgumentResolver;
import com.youn.intly.authentication.presentation.interceptor.AuthenticationInterceptor;
import com.youn.intly.authentication.presentation.interceptor.IgnoreAuthenticationInterceptor;
import com.youn.intly.authentication.presentation.interceptor.PathMatchInterceptor;
import com.youn.intly.configuration.auth_interceptor_register.AutoAuthorizationInterceptorRegister;
import com.youn.intly.configuration.auth_interceptor_register.UriParser;
import com.youn.intly.configuration.auth_interceptor_register.register_type.AuthenticateStorageForRegisterType;
import com.youn.intly.configuration.auth_interceptor_register.register_type.IgnoreAuthenticateStorageForRegisterType;
import com.youn.intly.configuration.auth_interceptor_register.register_type.StorageForRegisterType;
import com.youn.intly.configuration.auth_interceptor_register.scanner.ControllerScanner;
import com.youn.intly.configuration.auth_interceptor_register.scanner.ForLoginAndGuestScanner;
import com.youn.intly.configuration.auth_interceptor_register.scanner.ForOnlyLoginScanner;
import com.youn.intly.configuration.auth_interceptor_register.scanner.package_scanner.PackageScanner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {

    private static final String PACKAGE = "com.youn.intly";

    private final AuthService authService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver());
    }

    private AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(authService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        PathMatchInterceptor authenticationInterceptor =
                new PathMatchInterceptor(authenticationInterceptor());
        PathMatchInterceptor ignoreAuthenticationInterceptor =
                new PathMatchInterceptor(ignoreAuthenticationInterceptor());

        AutoAuthorizationInterceptorRegister autoAuthorizationInterceptorRegister =
                AutoAuthorizationInterceptorRegister.builder()
                        .storageForRegisterTypes(getStorageForRegisterTypes())
                        .authenticationInterceptor(authenticationInterceptor)
                        .ignoreAuthenticationInterceptor(ignoreAuthenticationInterceptor)
                        .uriParser(getUriParser())
                        .build();

        autoAuthorizationInterceptorRegister.execute();

        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(ignoreAuthenticationInterceptor)
                .addPathPatterns("/**");
    }


    private AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor(authService);
    }

    private IgnoreAuthenticationInterceptor ignoreAuthenticationInterceptor() {
        return new IgnoreAuthenticationInterceptor(authService);
    }

    private List<StorageForRegisterType> getStorageForRegisterTypes() {
        return List.of(
                new AuthenticateStorageForRegisterType(),
                new IgnoreAuthenticateStorageForRegisterType()
        );
    }

    private UriParser getUriParser() {
        return new UriParser(
                new ControllerScanner(parseClassesNames()),
                new ForLoginAndGuestScanner(),
                new ForOnlyLoginScanner()
        );
    }

    private List<String> parseClassesNames() {
        PackageScanner packageScanner = new PackageScanner(PACKAGE);
        return packageScanner.getAllClassNames();
    }
}