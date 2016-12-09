//package com.acetec.aps.server.common.aspect;
//
//
//import com.acetec.aps.server.exception.DisabledAccountException;
//import com.acetec.aps.server.exception.InvalidTokenException;
//import com.acetec.aps.server.exception.ResourceRequiredException;
//import com.acetec.aps.server.dao.UserDao;
//import com.acetec.aps.server.service.AuthenticateService;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class ResourceAspect {
//
//    @Autowired
//    UserDao userDao;
//
//    @Autowired
//    AuthenticateService authenticateService;
//
//
//    @Around("execution(* com.acetec.aps.server.service.impl.AuthenticateServiceImpl.*(..)) && @annotation(resourceRequired) && args(jwtToken, ..)")
//    public Object resourceRequired(ProceedingJoinPoint joinPoint, ResourceRequired resourceRequired, String jwtToken) throws Throwable {
//        try {
//            String username = authenticateService.getUsername(jwtToken);
//            String resourceName = resourceRequired.value();
//            if (hasResource(username, resourceName)) {
//                return joinPoint.proceed();
//            }
//            throw new ResourceRequiredException(resourceName);
//        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
//            throw new InvalidTokenException();
//        }
//    }
//
//    private boolean hasResource(String username, String resourceName) {
//        User user = userDao.findByUsername(username);
//        if (user.isDisabled()) {
//            throw new DisabledAccountException();
//        }
//        for (Role role : user.getRoles()) {
//            for (Resource resource : role.getResources()) {
//                if (resource.getName().equals(resourceName)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//}