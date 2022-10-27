# How to update the Keycloak Plugin
The Keycloak plugin is packaged in a plugin image. It will eventually be hosted in Iron Bank.
1. Be aware that there are currently two versions of Keycloak. One is the legacy version that uses Wildfly for the application server. The other version is the new one using Quarkus. This plugin supports the new Keycloak Keycloak Quarkus. The images in Iron Bank have tag without  ```X.X.X-legacy```.
1. Create a development branch and merge request. Can do this in the Gitlab UI from an issue.
1. Update /CHANGELOG.md with an entry for "upgrade Keycloak plugin to app version x.x.x. Or, whatever description is appropriate.
1. Update the keycloak library dependencies in the build.gradle file to match the new version of Keycloak. This Keycloak library update might cause build errors. You might have to fix code in `src/main/**.java` and `src/test/**.java` to get the build and unit tests to complete without errors.
1. Update any of the other gradle plugins as necessary. 
1. Follow instructions in the top-level README.md for how to build and deploy.

# Testing new Keycloak version
1. Create a k8s dev environment. One option is to use the Big Bang [k3d-dev.sh](https://repo1.dso.mil/platform-one/big-bang/bigbang/-/blob/master/docs/assets/scripts/developer/k3d-dev.sh) with the ```-m``` for metalLB so that k3d can support multiple ingress gateways. The following steps assume you are using the script.
1. Follow the instructions at the end of the script to ssh to the EC2 instance with application-level port forwarding. Keep this ssh session for the remainder of the testing. 
1. You will need to edit the /etc/hosts on the EC2 instance. Make it look like this
    ```bash
    ## begin bigbang.dev section
    172.20.1.240 keycloak.bigbang.dev
    172.20.1.241 gitlab.bigbang.dev sonarqube.bigbang.dev
    ## end bigbang.dev section
    ```
1. For end-to-end SSO testing there needs to be DNS for Keycloak. In a k3d dev environment there is no DNS so you must do a dev hack and edit the configmap "coredns-xxxxxxxx". Under NodeHosts add a host for keycloak.bigbang.dev.    
    ```
    kubectl get cm -n kube-system   
    kubectl edit cm coredns -n kube-system   
    ```

    The IP for keycloak in a k3d environment created by the dev script will be 172.20.1.240. Like this  
    ```yaml
    NodeHosts: |
    <nil>      host.k3d.internal
    172.20.0.2 k3d-k3s-default-agent-0
    172.20.0.5 k3d-k3s-default-agent-1
    172.20.0.4 k3d-k3s-default-agent-2
    172.20.0.3 k3d-k3s-default-server-0
    172.20.0.6 k3d-k3s-default-serverlb
    172.20.1.240 keycloak.bigbang.dev
    ```

1. Restart the coredns pod so that it picks up the new configmap.
    ```
    kubectl get pods -A   
    kubectl delete pod <coredns pod> -n kube-system
    ```

1. Deploy Big Bang with only istio-operator, istio, gitlab, and sonarqube enabled. Need to test both OIDC and SAML end-to-end SSO. Gitlab uses OIDC and Sonarqube uses SAML. Deploy BigBang using the following example helm command
    ```
    helm upgrade -i bigbang ./chart -n bigbang --create-namespace -f ../overrides/my-bb-override-values.yaml -f ../overrides/registry-values.yaml -f ./chart/ingress-certs.yaml
    ```
    The example values override configuration is in progress and will be provided when available. 

1. Sonarqube needs an extra configuration step for SSO to work because it uses SAML. The values override ```addons.sonarqube.sso.certificate``` needs to be updated with the Keycloak realm certificate. When Keycloak finishes installing login to the admin console [Keycloak](https://keycloak.bigbang.dev/auth/admin) with default credentials ```admin/password```. Navigate to Realm Settings >> Keys. On the RS256 row click on the ```Certificate``` button and copy the certificate text as a single line string and paste it into your ```addons.sonarqube.sso.certificate``` value. Run another ```helm upgrade``` command and watch for Sonarqube to update.
1. Use Firefox browser with SOCKS v5 manual proxy configured so that we are running Firefox as if it was running on the EC2 instance. This is described in more detail in the development environment addendum [Multi Ingress-gateway Support with MetalLB and K3D](https://repo1.dso.mil/platform-one/big-bang/bigbang/-/blob/master/docs/developer/development-environment.md)
1. In the Firefox browser load ```https://keycloak.bigbang.dev``` and register a test user. You should register yourself with CAC and also a non-CAC test.user with just user and password with OTP. Both flows need to be tested.
1. Then go back to ```https://keycloak.bigbang.dev/auth/admin``` and login to the admin console with the default credentials ```admin/password```
1. Navigate to users, click "View all users" button and edit the test users that you created. Set "Email Verified" ON. Remove the verify email "Required User Actions". Click "Save" button.
1. Test end-to-end SSO with Gitlab and Sonarqube with your CAC user and the other test user.
1. Test the custom user forms to make sure all the fields are working
    - https://keycloak.bigbang.dev/auth/realms/baby-yoda/account/
    - https://keycloak.bigbang.dev/auth/realms/baby-yoda/account/password
    - https://keycloak.bigbang.dev/auth/realms/baby-yoda/account/totp
    - https://keycloak.bigbang.dev/register

