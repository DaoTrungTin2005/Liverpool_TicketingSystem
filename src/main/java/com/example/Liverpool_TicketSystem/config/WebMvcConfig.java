package com.example.Liverpool_TicketSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// Đánh dấu lớp cấu hình
@Configuration

// tắt các cấu hình tự động (auto-configuration) của Spring Boot cho MVC
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public ViewResolver viewResolver() {
        // tạo một đối tượng InternalResourceViewResolver để xử lý các view JSP (gán vô
        // bean)
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();

        // cho phép sử dụng JSTL trong JSP
        bean.setViewClass(JstlView.class);

        // Các file JSP sẽ nằm trong thư mục /WEB-INF/view/.
        bean.setPrefix("/WEB-INF/view/");

        // Các file JSP sẽ có đuôi là .jsp
        bean.setSuffix(".jsp");
        return bean;
    }

    // Dùng để đăng ký thủ công view resolver với Spring MVC khi bạn muốn kiểm soát
    // rõ ràng thứ tự hoặc thêm nhiều view resolver khác nhau.

    // Khi bạn dùng @EnableWebMvc (tắt auto-configuration của Spring Boot), lúc này
    // Spring Boot sẽ không tự động nhận bean ViewResolver, bạn bắt buộc phải đăng
    // ký thủ công qua phương thức này.

    // Nếu đã có bean ViewResolver (dùng @Bean), Spring vẫn nhận và sử dụng nó để
    // render view JSP.

    // Override configureViewResolvers chỉ cần thiết khi bạn muốn đăng ký thủ công
    // hoặc cấu hình nhiều view resolver, hoặc không dùng @Bean.
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }

    @Override
    // Khi truy cập URL bắt đầu bằng /admin/ (ví dụ: /admin/style.css), Spring sẽ
    // tìm file trong thư mục /resources/admin/ của project.

    // Các cấu hình resource handler như trên chỉ áp dụng cho file tĩnh (CSS, JS,
    // ảnh), không ảnh hưởng đến JSP trong /WEB-INF/view/admin hay
    // /WEB-INF/view/client
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/admin/**")
                .addResourceLocations("/resources/admin/");
        registry.addResourceHandler("/client/**")
                .addResourceLocations("/resources/client/");
    }

}
