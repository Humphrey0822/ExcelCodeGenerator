//package com.ouyeel.config;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration;
//
//public class AppInitializer implements WebApplicationInitializer {
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        // TODO Auto-generated method stub
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(ApplicationConfig.class);
//        servletCon.addListener(new ContextLoaderListener(rootContext));
//
//        AnnotationConfigWebApplicationContext servletConfig = new AnnotationConfigWebApplicationContext();
//        servletConfig.register(DispatcherConfig.class);
//
//        ServletRegistration.Dynamic registration = servletCon.addServlet("dispatcher", new DispatcherServlet(servletConfig));
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/");
//    }
//}
