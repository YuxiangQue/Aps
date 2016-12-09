package com.acetec.aps.server.config;

import com.acetec.aps.server.common.OrikaUtils;
import com.acetec.aps.server.dao.PermissionDao;
import com.acetec.aps.server.dao.RoleDao;
import com.acetec.aps.server.dao.UserDao;
import com.acetec.aps.server.entity.*;
import com.acetec.aps.share.dto.*;
import com.corundumstudio.socketio.SocketIOServer;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import scala.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Configuration
@EnableJpaRepositories(basePackages = "com.acetec.aps.server.dao")
@EnableAspectJAutoProxy
public class Config {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Bean
    MapperFacade mapperFacade(MapperFactory mapperFactory) {
        return mapperFactory.getMapperFacade();
    }

    @Bean
    MapperFactory modelMapper() {
        MapperFactory mapper = OrikaUtils.createDefaultMapperFactory();
        mapper.classMap(Flow.class, FlowDto.class).byDefault().register();
        mapper.classMap(Product.class, ProductDto.class).byDefault().register();
        mapper.classMap(Lot.class, LotDto.class).byDefault().register();
        mapper.classMap(TesterModel.class, TesterModelDto.class).byDefault().register();
        mapper.classMap(TesterPlatform.class, TesterPlatformDto.class).byDefault().register();
        mapper.classMap(Tester.class, TesterDto.class).byDefault().register();
        mapper.classMap(User.class, UserDto.class).byDefault().register();
        mapper.classMap(Role.class, RoleDto.class).byDefault().register();
        mapper.classMap(Permission.class, PermissionDto.class).byDefault().register();
        return mapper;
    }


    CommandLineRunner socketIOServer() {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
                config.setHostname("localhost");
                config.setPort(9092);
                SocketIOServer server = new SocketIOServer(config);
                server.start();
            }
        };
    }

    // @Bean
    CommandLineRunner commandLineRunner() {
        return strings -> {

            List<Permission> userResources = new ArrayList<Permission>();
            userResources.add(new Permission("user:list"));
            userResources.add(new Permission("user-add"));
            userResources.add(new Permission("user-remove"));
            userResources.add(new Permission("user:edit"));

            List<Permission> roleResources = new ArrayList<Permission>();
            roleResources.add(new Permission("role:list"));
            roleResources.add(new Permission("role:add"));
            roleResources.add(new Permission("role:remove"));
            roleResources.add(new Permission("role:edit"));

            List<Permission> resourceResources = new ArrayList<Permission>();
            resourceResources.add(new Permission("resource:list"));
            resourceResources.add(new Permission("resource:add"));
            resourceResources.add(new Permission("resource:remove"));
            resourceResources.add(new Permission("resource:edit"));

            Set<Permission> adminResources = new HashSet<>();
            adminResources.addAll(userResources);
            adminResources.addAll(roleResources);
            adminResources.addAll(resourceResources);

            Set<Permission> productResources = new HashSet<>();
            productResources.add(new Permission("product:list"));
            productResources.add(new Permission("product:remove"));
            productResources.add(new Permission("product:edit"));
            productResources.add(new Permission("product:import"));
            productResources.add(new Permission("product:export"));
            productResources.add(new Permission("product:incomingmaterial"));

            Set<Permission> reportResources = new HashSet<Permission>();
            reportResources.add(new Permission("report:list"));
            reportResources.add(new Permission("report:export"));

            Set<Permission> scheduleResources = new HashSet<Permission>();
            scheduleResources.add(new Permission("schedule"));

            Set<Permission> lotResources = new HashSet<Permission>();
            lotResources.add(new Permission("lot:list"));
            lotResources.add(new Permission("lot:remove"));
            lotResources.add(new Permission("lot:edit"));
            lotResources.add(new Permission("lot:import"));
            lotResources.add(new Permission("lot:export"));

            Role adminRole = new Role("admin");
            adminRole.setPermissions(adminResources);
            Role productRole = new Role("product");
            productRole.setPermissions(productResources);
            Role reportRole = new Role("report");
            reportRole.setPermissions(reportResources);
            Role scheduleRole = new Role("schedule");
            scheduleRole.setPermissions(scheduleResources);
            Role lotRole = new Role("lot");
            lotRole.setPermissions(lotResources);

            userResources.forEach(r -> permissionDao.save(r));
            roleResources.forEach(r -> permissionDao.save(r));
            resourceResources.forEach(r -> permissionDao.save(r));
            productResources.forEach(r -> permissionDao.save(r));
            reportResources.forEach(r -> permissionDao.save(r));
            lotResources.forEach(r -> permissionDao.save(r));
            scheduleResources.forEach(r -> permissionDao.save(r));

            roleDao.save(adminRole);
            roleDao.save(productRole);
            roleDao.save(reportRole);
            roleDao.save(scheduleRole);
            roleDao.save(lotRole);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setSalt("");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(productRole);
            adminRoles.add(reportRole);
            adminRoles.add(scheduleRole);
            adminRoles.add(lotRole);
            admin.setRoles(adminRoles);
            userDao.save(admin);
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
