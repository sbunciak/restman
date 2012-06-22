restman
========================

What is it?
-----------

This is a school project for FI:PV243 (https://community.jboss.org/wiki/AdvancedJavaEELab). 
It's a J2EE 6 Web application built with Maven 3 and deployable to JBoss AS 7 or EAP6. 
This application is Java EE 6 compliant using JSF 2.0, CDI 1.0, EJB 3.1, JPA 2.0 and Bean Validation 1.0, etc.
Its purpose is to demonstrate capability of creating a sample reservations for the available restaurants, including all the managements it requires.  

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven
3.0 or better.

The application this project produces is designed to be run on a JBoss Enterprise Application Platform 6 or JBoss AS 7.
 
Since RESTMan is a Secured application, you need to change the configuration ($JBOSS_HOME/standalone/configuration/standalone.xml) as following:

*  Add RESTMan security domain in security submodule of AS7/EAP6:
				
				<security-domain name="restmanSecurity" cache-type="default">
                    <authentication>
                        <login-module code="org.jboss.security.auth.spi.DatabaseServerLoginModule" flag="required">
                            <module-option name="dsJndiName" value="java:jboss/datasources/restmanDS"/>
                            <module-option name="principalsQuery" value="select password from AbstractUser where email=?"/>
                            <module-option name="rolesQuery" value="select role, 'Roles' from AbstractUser where email=?"/>
                        </login-module>
                    </authentication>
                </security-domain>
                
As you may notice, RESTMan security domain uses datasource "restmanDS". To change the value of datasource you would like the application work with, you
need to edit persistence.xml and standalone.xml as well.

Deploying the application
-------------------------
 
First you need to start JBoss Enterprise Application Platform 6 or JBoss AS 7. To do this, run
  
    $JBOSS_HOME/bin/standalone.sh
  
or if you are using windows
 
    $JBOSS_HOME/bin/standalone.bat

To deploy the application, you first need to produce the archive to deploy using
the following Maven goal:

    mvn package

You can now deploy the artifact to JBoss AS by executing the following command:

    mvn jboss-as:deploy

This will deploy `target/restman.war`.
 
The application will be running at the following URL <http://localhost:8080/restman/>.

To undeploy from JBoss AS, run this command:

    mvn jboss-as:undeploy

You can also start JBoss AS 7 and deploy the project using Eclipse. See the JBoss AS 7 <a href="https://docs.jboss.org/author/display/AS71/Getting+Started+Developing+Applications+Guide" title="Getting Started Developing Applications Guide">Getting Started Developing Applications Guide</a> for more information.
 
Running the Arquillian tests
============================

By default, tests are configured to be skipped. The reason is that the sample
test is an Arquillian test, which requires the use of a container. You can
activate this test by selecting one of the container configuration provided 
for JBoss AS 7 (remote).

To run the test in JBoss AS 7, first start a JBoss AS 7 instance. Then, run the
test goal with the following profile activated:

    mvn clean test -Parq-jbossas-remote

To run Arquillian tests on OpenShift run:

	mvn clean test -Parquillian-openshift-express

You can also run Arquillian test from Eclipse IDE as basic JUnit tests. 
Just select appropriate maven profile (restman - maven - select maven profiles...) and then run Arquillian test (Run As - Junit test).

Importing the project into an IDE
=================================

If you created the project using the Maven archetype wizard in your IDE
(Eclipse, NetBeans or IntelliJ IDEA), then there is nothing to do. You should
already have an IDE project.

Detailed instructions for using Eclipse with JBoss AS 7 are provided in the 
JBoss AS 7 Getting Started Guide for Developers.

If you created the project from the commandline using archetype:generate, then
you need to import the project into your IDE. If you are using NetBeans 6.8 or
IntelliJ IDEA 9, then all you have to do is open the project as an existing
project. Both of these IDEs recognize Maven projects natively.

Downloading the sources and Javadocs
====================================

If you want to be able to debug into the source code or look at the Javadocs
of any library in the project, you can run either of the following two
commands to pull them into your local repository. The IDE should then detect
them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc

Source codes available on github.com
====================================

GitHub url: https://github.com/sbunciak/restman
