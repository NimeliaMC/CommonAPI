package fr.nimelia.api.server;

import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.provider.CloudServiceFactory;
import eu.cloudnetservice.driver.provider.CloudServiceProvider;
import eu.cloudnetservice.driver.provider.ServiceTaskProvider;
import eu.cloudnetservice.driver.service.*;
import fr.nimelia.api.CommonAPI;

public class ServerManager {

    ServiceTaskProvider serviceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider.class);
    CloudServiceFactory cloudServiceFactory = InjectionLayer.ext().instance(CloudServiceFactory.class);
    CloudServiceProvider cloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider.class);

    public void createInstance(ServerType serverType) {
        ServiceTask serviceTask = serviceTaskProvider.serviceTask(serverType.getName());
        System.out.println("blalalalala");
        cloudServiceFactory.createCloudServiceAsync(ServiceConfiguration.builder(serviceTask).build()).thenAcceptAsync(result -> {
            ServiceCreateResult.State state = result.state();
            if (!state.equals(ServiceCreateResult.State.CREATED)) {
                System.out.println("Erreur lors de la création du service (" + result.creationId() + ")");
                return;
            }
            System.out.println("Création du service avec succès (" + result.serviceInfo().name() + ")");
            ServerInstance instance = new ServerInstance(result.serviceInfo().name(), result.serviceInfo().address().port(),serverType, ServerStatus.CREATING);
            CommonAPI.getApi().getRedisCredentials().saveServerInstance(instance);
            result.serviceInfo().provider().start();
        }).join();
    }

    public void deleteInstance(String serverName) {
        ServiceInfoSnapshot service = cloudServiceProvider.serviceByName(serverName);
        if(service == null){
            System.out.println("error instance not found");
            return;
        }
        service.provider().delete();
        CommonAPI.getApi().getRedisCredentials().deleteServerInstance(serverName);
    }

}