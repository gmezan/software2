echo "Warning running: sudo rm -r /var/sw2/sw2.jar"
echo "For deployment information, go to: https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment-systemd-service"
sudo rm -r /var/sw2/sw2.jar
sudo git pull
sudo mvn package
sudo cp target/sw2-0.0.1-SNAPSHOT.jar /var/sw2/sw2.jar
echo "Script finished"
