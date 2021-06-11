
# Oracle SOA SUITE - Deployments com Oracle Visual Builder Studio

O objetivo deste documento é proporcionar a construção de um projeto em **SOA SUITE** e que este possa ser implantado automaticamente através de processo de **DevOps** com o **Oracle Visual Builder Studio**.

O **Oracle Visual Builder Studio** é a ferramenta oficial da **Oracle** para automação de processos **DevOPs** com as mais diferentes tecnologias, permitindo assim trabalhar com tecnologias como:

* Kubernetes/Docker
* functions
* Servidores de Aplicação como **Oracle Weblogic**, IIS, JBoss, Tomcat
* **Oracle Forms**
* **Oracle SOA SUITE**
* **Oracle Integration (OIC)**
* Entre várias outras tecnologias

ESTE ARTIGO AINDA NÃO ESTÁ COMPLETO. FAVOR AGUARDAR A FINALIZAÇÃO

### Build com Maven

Antes de mais nada, para acessar o arquivo **pom.xml**, é preciso ter a visão do diretório do projeto. Para isto, você precisa ativar esta visão.
Clique em "Application Windows Options" com na figura a seguir:

![View-maven-pom-xml-file.png](https://github.com/hoshikawa2/repo-image/blob/master/View-maven-pom-xml-file.png?raw=true)

E selecione a opção "Directory View". Assim você conseguirá acessar o arquivo **pom.xml** que se encontra na estrutura **/SOA/SCA-INF/pom.xml**

![File-pom-xml-file2.png](https://github.com/hoshikawa2/repo-image/blob/master/File-pom-xml-file2.png?raw=true)

Agora clique duas vezes no arquivo **pom.xml** para abri-lo no editor do **JDeveloper** e selecione a tab "Source" para visualizar o código:

![pom-xml-structure-file.png](https://github.com/hoshikawa2/repo-image/blob/master/pom-xml-structure-file.png?raw=true)

Assim você conseguirá visualizar o código do arquivo **pom.xml**

![pom-xml-original.png](https://github.com/hoshikawa2/repo-image/blob/master/pom-xml-original.png?raw=true)

Aqui uma alteração muito importante para o processo de **DevOps**. Você **PRECISA** incluir estas linhas para a automação:

    Entre a tag <build> e a tag <plugins> você poderá incluir este código, o qual será responsável pela mágica da montagem do pacote de seu software para o deployment posterior com o **Ant**:
    
    <!-- Para automação DevOps, necessita incluir as 3 linhas abaixo -->
    <directory>target</directory>
    <outputDirectory>classes</outputDirectory>
    <sourceDirectory>src</sourceDirectory>



![devops-pom-xml.png](https://github.com/hoshikawa2/repo-image/blob/master/devops-pom-xml.png?raw=true)


Logo, o seu código completo ficaria assim:

    pom.xml
    
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>

      <groupId>soakafka</groupId>
      <artifactId>kafka</artifactId>
      <version>1.0-SNAPSHOT</version>

      <dependencies>
        <dependency>
          <groupId>org.apache.kafka</groupId>
          <artifactId>kafka-clients</artifactId>
          <version>1.0.0</version>
        </dependency>
      </dependencies>

      <build>

        <!-- Para automação DevOps, necessita incluir as 3 linhas abaixo - Cristiano Hoshikawa - 2020-11-28 -->
        <directory>target</directory>
        <outputDirectory>classes</outputDirectory>
        <sourceDirectory>src</sourceDirectory>

        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.1</version>
            <configuration>
              <source>1.8</source>
              <target>1.8</target>
            </configuration>
          </plugin>
          <plugin>
            <artifactId>maven-assembly-plugin</artifactId>
            <configuration>
              <archive>
                <manifest>
                  <mainClass>KafkaExample</mainClass>
                </manifest>
              </archive>
              <descriptorRefs>
                <descriptorRef>jar-with-dependencies</descriptorRef>
              </descriptorRefs>
            </configuration>
            <executions>
              <execution>
                <id>make-assembly</id> <!-- this is used for inheritance merges -->
                <phase>package</phase> <!-- bind to the packaging phase -->
                <goals>
                  <goal>single</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.0.2</version>
            <configuration>
              <archive>
                <manifest>
                  <mainClass>KafkaExample</mainClass>
                  <addClasspath>true</addClasspath>
                </manifest>
              </archive>
            </configuration>
          </plugin>
        </plugins>
      </build>  
    </project>



### Build e Deployment com Ant

**Ant** é uma ferramenta já bastante conhecida no mercado, desenvolvida pelo projeto **Jakarta**, responsável por automatizar compilações, builds de pacotes e deployments de aplicações para projetos como o Java.

No processo de **DevOps** o **Ant** será fundamental para a construção do pacote SOA SUITE e também para o deployment deste pacote no servidor do **Weblogic SOA SUITE** remoto.

O arquivo **build.xml** é um arquivo de configuração muito comum para utilização com o **Ant**. O formato deste arquivo é no formato **XML**



    build.xml
    
    <?xml version="1.0" encoding="UTF-8" ?>
    <!--Ant buildfile generated by Oracle JDeveloper-->
    <!--Generated Oct 12, 2020 11:35:33 PM-->
    <project xmlns="antlib:org.apache.tools.ant" name="SOAKafkaProducerPrj" default="all" basedir=".">
      <property environment="env" /> 
      <property file="build.properties"/>
      <path id="library.SOA.Designtime">
        <pathelement location="${install.dir}/soa/plugins/jdeveloper/extensions/oracle.sca.modeler.jar"/>
      </path>
      <path id="library.SOA.Runtime">
        <pathelement location="${install.dir}/soa/soa/modules/oracle.soa.fabric_11.1.1/fabric-runtime.jar"/>
        <pathelement location="${install.dir}/soa/soa/modules/oracle.soa.fabric_11.1.1/tracking-api.jar"/>
        <pathelement location="${install.dir}/soa/soa/modules/oracle.soa.fabric_11.1.1/tracking-core.jar"/>
        <pathelement location="${install.dir}/soa/soa/modules/oracle.soa.fabric_11.1.1/edn.jar"/>
        <pathelement location="${install.dir}/soa/soa/modules/oracle.soa.mgmt_11.1.1/soa-infra-mgmt.jar"/>
        <pathelement location="${oracle.commons}/modules/com.oracle.webservices.fabric-common-api.jar"/>
      </path>
      <path id="library.BPEL.Runtime">
        <pathelement location="${install.dir}/soa/soa/modules/oracle.soa.bpel_11.1.1/orabpel.jar"/>
      </path>
      <path id="library.Mediator.Runtime">
        <pathelement location="${install.dir}/soa/soa/modules/oracle.soa.mediator_11.1.1/mediator_client.jar"/>
      </path>
      <path id="library.MDS.Runtime">
        <pathelement location="${oracle.commons}/modules/oracle.mds/mdsrt.jar"/>
      </path>
      <path id="library.BC4J.Service.Runtime">
        <pathelement location="${oracle.commons}/modules/oracle.adf.model/adfbcsvc.jar"/>
        <pathelement location="${oracle.commons}/modules/oracle.adf.model/adfbcsvc-share.jar"/>
        <pathelement location="${oracle.commons}/modules/commonj.sdo.backward.jar"/>
        <pathelement location="${oracle.commons}/modules/commonj.sdo.jar"/>
        <pathelement location="${oracle.commons}/modules/oracle.toplink/eclipselink.jar"/>
        <pathelement location="${oracle.commons}/modules/com.oracle.webservices.fmw.wsclient-impl.jar"/>
        <pathelement location="${oracle.commons}/modules/com.oracle.webservices.fmw.jrf-ws-api.jar"/>
        <pathelement location="${oracle.commons}/modules/com.oracle.webservices.fmw.web-common-schemas-impl.jar"/>
      </path>
      <path id="classpath">
        <path refid="library.SOA.Designtime"/>
        <path refid="library.SOA.Runtime"/>
        <path refid="library.BPEL.Runtime"/>
        <path refid="library.Mediator.Runtime"/>
        <path refid="library.MDS.Runtime"/>
        <path refid="library.BC4J.Service.Runtime"/>
      </path>
      <target name="init">
        <tstamp/>
        <mkdir dir="${workspace}/SOAKafkaProducerApp/SOAKafkaProducerPrj/SOA/SCA-INF/classes"/>
      </target>
      <target name="all" description="Build the project" depends="deploy,copy"/>
      <target name="clean" description="Clean the project">
        <delete includeemptydirs="true" quiet="true">
          <fileset dir="${output.dir}" includes="**/*"/>
        </delete>
      </target>
      <target name="compile" depends="init">
          <javac 
                   srcdir="${workspace}/SOAKafkaProducerApp/SOAKafkaProducerPrj/SOA/SCA-INF/src/soakafka"
                   destdir="${workspace}/SOAKafkaProducerApp/SOAKafkaProducerPrj/SOA/SCA-INF/classes"
               includeantruntime="false">
               <include name="${workspace}/SOAKafkaProducerApp/SOAKafkaProducerPrj/SOA/SCA-INF/lib/**"/>
        </javac>
      </target>
      <target name="sca-compile" depends="compile">
          <ant antfile="${middleware.home}/soa/bin/ant-sca-compile.xml" inheritAll="false">
               <property name="scac.input" value="${workspace}/SOAKafkaProducerApp/SOAKafkaProducerPrj/SOA/composite.xml"/>
          </ant>
      </target>
     
      <target name="sca-package" depends="sca-compile">
          <ant antfile="/${middleware.home}/soa/bin/ant-sca-package.xml" inheritAll="false">
               <property name="compositeDir" value="${workspace}/SOAKafkaProducerApp/SOAKafkaProducerPrj/SOA"/>
              <property name="compositeName" value="SOAKafkaProducerPrj"/>
              <property name="revision" value="${revision}"/>
              <property name="sca.application.home" value="${workspace}/SOAKafkaProducerApp/SOAKafkaProducerPrj"/>
          </ant>
      </target>
            
      <target name="deploy" description="Deploy JDeveloper profiles" depends="sca-package">
        <taskdef name="ojdeploy" classname="oracle.jdeveloper.deploy.ant.OJDeployAntTask" uri="oraclelib:OJDeployAntTask"
                 classpath="${oracle.jdeveloper.ant.library}"/>
        <ora:ojdeploy xmlns:ora="oraclelib:OJDeployAntTask" executable="${oracle.jdeveloper.ojdeploy.path}"
                      ora:buildscript="${oracle.jdeveloper.deploy.dir}/ojdeploy-build.xml"
                      ora:statuslog="${oracle.jdeveloper.deploy.dir}/ojdeploy-statuslog.xml">
          <ora:deploy>
            <ora:parameter name="workspace" value="${oracle.jdeveloper.workspace.path}"/>
            <ora:parameter name="project" value="${oracle.jdeveloper.project.name}"/>
            <ora:parameter name="profile" value="${oracle.jdeveloper.deploy.profile.name}"/>
            <ora:parameter name="nocompile" value="false"/>
            <ora:parameter name="outputfile" value="${oracle.jdeveloper.deploy.outputfile}"/>
          </ora:deploy>
        </ora:ojdeploy>
     
         <!-- Deployment SOA SUITE Composite -->
        <ant antfile="/${middleware.home}/soa/bin/ant-sca-deploy.xml" target="deploy" inheritall="false">
          <property name="user"      value="${WEBLOGICUSER}"/>
          <property name="password"  value="${WEBLOGICPWD}"/>
          <property name="serverURL"     value="${WEBLOGICURL}"/>
          <property name="sarLocation"   value="${workspace}/SOAKafkaProducerApp/SOAKafkaProducerPrj/SOA/deploy/sca_SOAKafkaProducerPrj_rev${revision}.jar"/>
          <property name="overwrite"     value="true"/>
        </ant>

      </target>
      <target name="copy" description="Copy files to output directory" depends="init">
        <patternset id="copy.patterns">
          <include name="**/*.GIF"/>
          <include name="**/*.JPEG"/>
          <include name="**/*.JPG"/>
          <include name="**/*.PNG"/>
          <include name="**/*.cpx"/>
          <include name="**/*.dcx"/>
          <include name="**/*.ejx"/>
          <include name="**/*.gif"/>
          <include name="**/*.ini"/>
          <include name="**/*.jpeg"/>
          <include name="**/*.jpg"/>
          <include name="**/*.png"/>
          <include name="**/*.properties"/>
          <include name="**/*.sva"/>
          <include name="**/*.tag"/>
          <include name="**/*.tld"/>
          <include name="**/*.wsdl"/>
          <include name="**/*.xcfg"/>
          <include name="**/*.xlf"/>
          <include name="**/*.xml"/>
          <include name="**/*.xsd"/>
          <include name="**/*.xsl"/>
          <include name="**/*.exm"/>
          <include name="**/*.xml"/>
          <exclude name="build.xml"/>
        </patternset>
        <copy todir="${output.dir}">
          <fileset dir="SOA/SCA-INF/src">
            <patternset refid="copy.patterns"/>
          </fileset>
          <fileset dir=".">
            <patternset refid="copy.patterns"/>
          </fileset>
        </copy>
      </target>
    </project>


O arquivo **build.properties** determina as propriedades que serão utilizadas no arquivo de configuração **build.xml** 

    build.properties
    
    oracle.commons=../../../../oracle_common/
    install.dir=../../../..
    oracle.home=${env.ORACLE_HOME_SOA_12_2_1}
    oracle.jdeveloper.workspace.path=${env.WORKSPACE}/SOAKafkaProducerApp/SOAKafkaProducerApp.jws
    middleware.home=${env.MIDDLEWARE_HOME_SOA_12_2_1}
    workspace=${env.WORKSPACE}
    oracle.jdeveloper.ant.library=${env.ORACLE_HOME_SOA_12_2_1}/jdev/lib/ant-jdeveloper.jar
    oracle.jdeveloper.deploy.dir=${env.WORKSPACE}/SOAKafkaProducerApp/SOAKafkaProducerPrj/deploy
    oracle.jdeveloper.ojdeploy.path=${oracle.home}/jdev/bin/ojdeploy
    javac.nowarn=off
    oracle.jdeveloper.project.name=SOAKafkaProducerPrj
    revision=1.0
    oracle.jdeveloper.deploy.outputfile=${env.WORKSPACE}/SOAKafkaProducerApp/SOAKafkaProducerPrj/deploy/sca_${profile.name}_rev{$revision}
    output.dir=classes
    javac.deprecation=off
    oracle.jdeveloper.deploy.profile.name=*
    javac.debug=on
    WEBLOGICPWD=${env.WEBLOGICPWD}
    WEBLOGICURL=${env.WEBLOGICURL}
    WEBLOGICUSER=${env.WEBLOGICUSER}
    

### O Servidor Weblogic SOA SUITE

### Automatizando o Deployment com o Oracle Visual Builder Studio


