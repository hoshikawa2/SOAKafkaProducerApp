
# Oracle SOA SUITE - Deployments com Oracle Visual Builder Studio

O objetivo deste documento é proporcionar a construção de um projeto em **SOA SUITE** e que este possa ser implantado automaticamente através de processo de **DevOps** com o **Oracle Visual Builder Studio**.

O **Oracle Visual Builder Studio** é a ferramenta oficial da **Oracle** para automação de processos **DevOps** com as mais diferentes tecnologias, permitindo assim trabalhar com:

* Kubernetes/Docker
* functions
* Servidores de Aplicação como **Oracle Weblogic**, IIS, JBoss, Tomcat
* **Oracle Forms**
* **Oracle SOA SUITE**
* **Oracle Integration (OIC)**
* Entre várias outras tecnologias

ESTE ARTIGO AINDA NÃO ESTÁ COMPLETO. FAVOR AGUARDAR A FINALIZAÇÃO

### Criando sua instância de Oracle Streaming

Primeiramente, vamos criar um grupo de usuários para Streaming e habilitar os usuários neste grupo.
Feito isto, podemos então determinar os acessos a este grupo através das Policies.
Abaixo, veja um exemplo de como determinar os acessos para utilizar o Oracle Cloud Streaming:

![oracle-stream-policies.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-policies.png?raw=true)

Agora vamos criar a instância do Oracle Cloud Streaming. No menu principal, selecione a opção "Analytics & AI" e "Streaming" conforme a figura abaixo.

![oracle-streaming-menu.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-streaming-menu.png?raw=true)

Feito isto, você verá a console com as instâncias de Oracle Cloud Streaming.
Para criar sua instância, selecione o compartimento e clique em "Create Stream"

![oracle-stream-create-1.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-create-1.png?raw=true)

Após esta etapa, preencha os dados de sua instância, como o nome, compartimento.
Além disto, solicite que seja criada um novo Pool de Stream e forneça um nome para seu pool. Clique em "Create".

![oracle-stream-create-2.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-create-2.png?raw=true)

Aguarde que a criação fique completa.

![oracle-stream-complete-create.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-complete-create.png?raw=true)

### Obtendo as configurações da REST API compatível com Kafka

Vamos agora obter as configurações da API REST do Kafka clicando no link "Kafka Connect Configurations" conforme abaixo:

![oracle-stream-kafka-connection-1.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-kafka-connection-1.png?raw=true)

Vamos então criar a configuração selecionando o compartimento e clicando na opção "Create Kafka Connect Configuration"

![oracle-stream-create-kafka-conn.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-create-kafka-conn.png?raw=true)

Preencha o compartimento e o nome de sua configuração conforme abaixo. Crie a configuração clicando em "Create Kafka Connect Configuration"

![oracle-stream-kafka-config.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-kafka-config.png?raw=true)

Vamos buscar os parâmetros de configuração para conectar ao Oracle Cloud Stream.
Vá até a opção "Streams" nos links à esquerda:

![oracle-stream-link.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-link.png?raw=true)

E logo após, clique no seu Pool criado anteriormente:

![oracle-stream-default-pool.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-default-pool.png?raw=true)

E por último, clique em "Kafka Connection Settings"

![oracle-stream-parameters.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-parameters.png?raw=true)

Pronto! Copie os parâmetros para utilizar em seu código Java:

![oracle-stream-parameters-view.png](https://github.com/hoshikawa2/repo-image/blob/master/oracle-stream-parameters-view.png?raw=true)


### Criando seu projeto SOA SUITE

Nesta etapa, vamos criar um projeto com os seguintes objetivos:

* Projeto para produzir e consumir uma fila de Streaming como o Kafka
* Serviço REST para testar ambos os serviços

#
    Iremos criar uma instância do Oracle Cloud Streaming com a finalidade de substituir o Kafka.
    O Oracle Cloud Streaming é a solução gerenciada da cloud Oracle para streaming de dados, com API REST compatível com o projeto open Kafka. Logo, a implementação aqui será compatível com o Kafka também se desejar alterar o apontamento para uma fila Kafka criada em uma máquina virtual ou mesmo em sua estrutura on-premisses

O projeto completo está disponível com esta documentação, portanto, você pode simplesmente alterar as configurações para pode compilar, montar o pacote e implantar em sua instância de SOA SUITE.

Baixe o projeto **SOAKafkaProducerApp** e abra as pastas até encontrar o arquivo de aplicação jws:

    /SOAKafkaProducerApp/SOAKafkaProducerApp/SOAKafkaProducerApp.jws
    
Assim você poderá visualizar no **JDeveloper** os projetos
* SOAKafkaProducerPrj
* SOAKafkaConsumerPrj

Clique na árvore da aplicação e procure por **SOAKakfaConsumerPrj** e dê duplo-clique conforme a imagem:

![jdev-soa-1.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-soa-1.png?raw=true)

Esta é a visão do projeto SOA para consumo da fila Kafka. Esta é uma implementação típica do tipo **SOA Composite**. Trata-se de um serviço REST (componente disposto na raia **Exposed Services**) e deve ser implantado no SOA Server. O componente ligado ao serviço é a implementação do serviço propriamente e está disposto na raia **Components** conforme a imagem anterior.
Ao dar um duplo-clique no componente KafkaConsumer você será direcionado para a implementação do serviço conforme abaixo:

![jdev-bpel-1.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-bpel-1.png?raw=true)

A primeira etapa de implementação inicia-se com um componente chamado Receive, responsável por receber parâmetro(s) iniciais de trabalho, típicos de uma chamada REST. Neste exemplo, não teremos passagem de parâmetros, mas fica aqui ilustrado para serviços que necessitarem deste recurso:

![jdeve-receive-1.png](https://github.com/hoshikawa2/repo-image/blob/master/jdeve-receive-1.png?raw=true)

![jdev-receive-detail.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-receive-detail.png?raw=true)

![jdev-bpel-code.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-bpel-code.png?raw=true)

O componente Java Embedded é responsável pela chamada a uma rotina Java que, neste caso irá chamar uma classe chamada KafaExample comentado mais adiante:

![jdev-embedded-code-1.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-embedded-code-1.png?raw=true)

O código Java chamado neste Java Embedding é responsável pelas rotinas de produção e consumo da fila Kafka ou Oracle Strem (lembre-se de que a API para o Oracle Streaming é compatível com o Kakfa):

```java
		package soakafka;

		import org.apache.kafka.clients.consumer.ConsumerRecord;
		import org.apache.kafka.clients.consumer.ConsumerRecords;
		import org.apache.kafka.clients.consumer.KafkaConsumer;
		import org.apache.kafka.clients.producer.KafkaProducer;
		import org.apache.kafka.clients.producer.Producer;
		import org.apache.kafka.clients.producer.ProducerRecord;
		import org.apache.kafka.common.serialization.StringSerializer;
		import org.apache.kafka.common.serialization.StringDeserializer;

		import java.util.Arrays;
		import java.util.Date;
		import java.util.Properties;

		import java.util.concurrent.ExecutionException;

		import org.apache.kafka.clients.producer.Callback;
		import org.apache.kafka.common.errors.WakeupException;

		public class KafkaExample {
		    private final String topic;
		    private final Properties props;

		    public KafkaExample(String brokers, String username, String password) {
		        this.topic = "kafka_like";

		        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
		        String jaasCfg = String.format(jaasTemplate, username, password);

		        String serializer = StringSerializer.class.getName();
		        String deserializer = StringDeserializer.class.getName();
		        //Propriedades
		        props = new Properties();
		        props.put("bootstrap.servers", brokers);
		        props.put("group.id", "kafka-hoshikawa");
		        props.put("enable.auto.commit", "false");
		        props.put("max.poll.records", "10");
		        props.put("auto.offset.reset", "earliest");
		        props.put("key.deserializer", deserializer);
		        props.put("value.deserializer", deserializer);
		        props.put("security.protocol", "SASL_SSL");
		        props.put("sasl.mechanism", "PLAIN");
		        props.put("sasl.jaas.config", jaasCfg);
		        //props.put("ssl.client.auth", "requested");
		    }

		    public String consume() {
		        String ret = "";
		        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		        consumer.subscribe(Arrays.asList(topic));

		        try {
		          while (true) {
		            ConsumerRecords<String, String> records = consumer.poll(100);
		            for (ConsumerRecord<String, String> record : records)
		            {
		              System.out.println(record.offset() + ": " + record.value());
		              ret = ret + record.value();
		            }
		              if (ret != "")
		                break;
		          }
		        } catch (Exception e) {
		          // ignore for shutdown
		        } finally {
		          consumer.commitAsync();
		          consumer.close();
		        }
		        return ret;
		    };

		    public void produce(String message) {
		        Producer<String, String> producer = new KafkaProducer<String, String>(props);
		        ProducerRecord record = new ProducerRecord<String, String>(topic, "msg", message);

		        Callback callback = (data, ex) -> {
		            if (ex != null) {
		                ex.printStackTrace();
		                return;
		            }
		            System.out.println(
		                "Mensagem enviada com sucesso para: " + data.topic() + " | partition " + data.partition() + "| offset " + data.offset() + "| tempo " + data
		                    .timestamp());
		        };
		        try {
		            producer.send(record, callback).get();
		        } catch (ExecutionException | InterruptedException e) {
		        }
		        finally {
		            producer.close();
		        }
		    }

		    public static void main(String[] args) {
		                /*
				String brokers = System.getenv("CLOUDKARAFKA_BROKERS");
				String username = System.getenv("CLOUDKARAFKA_USERNAME");
				String password = System.getenv("CLOUDKARAFKA_PASSWORD");
		                */
		                String brokers = "cell-1.streaming.us-ashburn-1.oci.oraclecloud.com:9092";
		                String username = "hoshikawaoraclecloud/oracleidentitycloudservice/hoshikawa2@hotmail.com/ocid1.streampool.oc1.iad.amaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxkgztajzakb5a";
		                String password = "Wxxxxxxxxxxxxxxxxxxxxxxk";
				KafkaExample c = new KafkaExample(brokers, username, password);
		        c.consume();
		    }
		}

```
![jdev-assign-1.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-assign-1.png?raw=true)

![jdev-assign-details.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-assign-details.png?raw=true)

![jdev-reply-1.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-reply-1.png?raw=true)

![jdev-reply-details.png](https://github.com/hoshikawa2/repo-image/blob/master/jdev-reply-details.png?raw=true)



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

```xml
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


```
### Build e Deployment com Ant

**Ant** é uma ferramenta já bastante conhecida no mercado, desenvolvida pelo projeto **Jakarta**, responsável por automatizar compilações, builds de pacotes e deployments de aplicações para projetos como o Java.

No processo de **DevOps** o **Ant** será fundamental para a construção do pacote SOA SUITE e também para o deployment deste pacote no servidor do **Weblogic SOA SUITE** remoto.

O arquivo **build.xml** é um arquivo de configuração muito comum para utilização com o **Ant**. O formato deste arquivo é no formato **XML**


```xml
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

```

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
    
### Inicializando o ambiente SOA SUITE para testes

Primeiramente abra a porta do firewall de seu ambiente SOA SUITE. A porta a ser liberada é a 9092.

### Executando o Deployment Manual no SOA SUITE

![soa-deploy-1.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-1.png?raw=true)

![soa-deploy-2.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-2.png?raw=true)

![soa-deploy-3.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-3.png?raw=true)

![soa-deploy-4.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-4.png?raw=true)

![soa-deploy-5.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-5.png?raw=true)

![soa-deploy-6.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-6.png?raw=true)

![soa-deploy-8.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-8.png?raw=true)

![soa-deploy-9.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-9.png?raw=true)

![soa-deploy-10.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-10.png?raw=true)

![soa-deploy-11.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-deploy-11.png?raw=true)

### Instalando o certificado JKS no Weblogic:

Em ambientes SSL será necessário criar uma segurança através de chaves (pública/privada) o qual será feito através de certificados JKS, próprios para o ambiente WebLogic.
O processo de geração da chave é feito através da obtenção do certificado de sua instância de **Oracle Cloud Streaming** e adição deste certificado a um arquivo **JKS**, o qual chamaremos de **DemoTrust** (DemoTrust.jks).

    Ponto importante:
    O arquivo DemoTrust.jks será carimbado com um ALIAS e também com uma senha 
    o qual será utilizado em seu código Java

Após criar seu arquivo JKS, você poderá mover este arquivo para a estrutura do seu servidor WebLogic.

1. Obtendo o certificado do Oracle Cloud Streaming

#
    https://docs.oracle.com/en/cloud/paas/integration-cloud/stream-service-adapter/prerequisites-creating-connection.html

2. Gerando o arquivo JKS
3. Instalando o arquivo JKS no Weblogic

#


	echo -n | openssl s_client -connect cell-1.streaming.us-ashburn-1.oci.oraclecloud.com:9092 | sed -ne  '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > ociStreaming.cert
	keytool -keystore Streaming_truststore.jks -alias OSSStream -import -file ociStreaming.cert
	
	sudo keytool -importcert -file ociStreaming.cert -keystore DemoTrust.jks -alias "kafka" -storepass DemoTrustKeyStorePassPhrase
	
Por exemplo:

	echo -n | openssl s_client -connect cell-1.streaming.us-ashburn-1.oci.oraclecloud.com:9092 | sed -ne  '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > ociStreaming.cert
	keytool -keystore Streaming_truststore.jks -alias OSSStream -import -file ociStreaming.cert

	sudo keytool -importcert -file ociStreaming.cert -keystore DemoTrust.jks -alias "kafka" -storepass DemoTrustKeyStorePassPhrase

Caso você queira gerar ou deletar as keystores por alias, siga esses passos:

    sudo keytool -import -v -file streaming.us-ashburn-1.oci.oraclecloud.com.cer -storepass changeit -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/security/cacerts -alias "kafka" 

    sudo keytool -delete -alias kafka -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/security/cacerts -storepass changeit

    sudo keytool -import -v -file streaming.us-ashburn-1.oci.oraclecloud.com.cer -keystore DemoTrust.jks -alias "kafka" -storepass DemoTrustKeyStorePassPhrase

    sudo keytool -delete -alias kafka -keystore DemoTrust.jks -storepass DemoTrustKeyStorePassPhrase

Vá para a pasta 
	
	Encontre sua pasta do wlserver que normalmente possui este caminho:
	/wlserver/server/lib
	
	Por exemplo:
	cd /u01/app/oracle/middleware/wlserver/server/lib
	cd /User/u01/Oracle/Middleware/Oracle_Home/wlserver/server/lib


E substitua o arquivo DemoTrust.jks pelo gerado nesta etapa.

Após copiar o arquivo DemoTrust.jks, você deve alterar o diretório no qual você implantou o arquivo. Altere os arquivos /src/soakafka/KafkaExemplo.java de ambos os projetos (SOAKafkaProducerprj e SOAKafkaConsumerprj) na criação da classe conforme abaixo:

![change-truststore-soa-1.png](https://github.com/hoshikawa2/repo-image/blob/master/change-truststore-soa-1.png?raw=true)


### Testando a aplicação

Após a subida do ambiente de testes, sua instância estará disponível em:

    http://localhost:7101/em
    
    Lembre-se de seu usuário e senha

![soa-test-1.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-test-1.png?raw=true)

![soa-test-2.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-test-2.png?raw=true)

![soa-test-3.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-test-3.png?raw=true)

![soa-test-4.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-test-4.png?raw=true)

![soa-test-5.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-test-5.png?raw=true)

Preencha o parâmetro de mensagem exigido pelo serviço, conforme a implementação. Este parâmetro foi chamado de "msg" e o formato da mensagem é JSON conforme os passos anteriores:

![soa-test-producer-1a.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-test-producer-1a.png?raw=true)

Após inserir sua mensagem no formato JSON, clique em "Test Web Service" para executar seu serviço de Producer na fila de mensagens:

![soa-test-producer-2.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-test-producer-2.png?raw=true)


### O Servidor Weblogic SOA SUITE

Você deve possuir uma instância ativa do **Oracle SOA SUITE** para que sua aplicação possa ser implantada.
A instância a ser utilizada neste tutorial será a imagem disponibilizada em nosso **Marketplace** e contará com uma estrutura de SOA SUITE servida por um **bastion** para proteger o servidor.

Para isto, vamos criar uma instância. Na console da **Oracle Cloud**, clique no menu principal (no canto esquerdo superior da tela), opção "Marketplace" e "All Applications" conforme abaixo:

![market-place.png](https://github.com/hoshikawa2/repo-image/blob/master/market-place.png?raw=true)

E na tela de busca de imagens, digite "soa suite" para encontrar a imagem correta:

![marketplace-soa-suite.png](https://github.com/hoshikawa2/repo-image/blob/master/marketplace-soa-suite.png?raw=true)


Clique na opção "Oracle SOA Suite (BYOL)" para iniciar a criação da instância. Você verá algumas opções obrigatórias para responder.

    Confime a versão do SOA SUITE: 12.2.1.4 ou superior (este workshop foi desenvolvido com a versão 12.2.1.4)
    Selecione o compartimento no qual você deseja que a instância de SOA SUITE seja criada
    Confirme o aceite dos termos e condições do contrato de licenciamento

    E clique no botão "Launch Stack":

![soa-suite-creation.png](https://github.com/hoshikawa2/repo-image/blob/master/soa-suite-creation.png?raw=true)


Maiores detalhes na criação estarão neste documento:

https://docs.oracle.com/en/cloud/paas/soa-cloud/soa-marketplace/soamp-create-and-view-oracle-soa-suite-instances.html

Selecione o menu principal novamente na console da **Oracle Cloud**, Compute e Instances:

![compute-select.png](https://github.com/hoshikawa2/repo-image/blob/master/compute-select.png?raw=true)


Selecione o compartimento o qual você criou sua instância do SOA SUITE e ao selecioná-la você verá 2 máquinas virtuais: 

* O Servidor SOA SUITE 
* A VM que servirá de bastion para proteção do servidor SOA SUITE

A VM bastion terá um IP público, o qual será possível realizar o acesso via Internet para acesso ao servidor SOA SUITE por intermédio da abertura de um túnel IP, explicado mais adiante.

![instance-soa-suite-compute.png](https://github.com/hoshikawa2/repo-image/blob/master/instance-soa-suite-compute.png?raw=true)


Para estabelecer um túnel entre o Servidor Bastion e a instância do SOA SUITE, você poderá fazê-lo através do comando SSH conforme abaixo:

    ssh -Y -i <Arq.Chave SSH> -L <Port SOA>:<IP Instancia SOA SUITE>:<Port External> opc@<IP Público Bastion>
    
    Exemplo:
    ssh -Y -i chavesrvbastion.pem -L 7002:10.253.128.9:7002 opc@152.67.55.11
    
    Após estabelecer a conexão via Bastion, será possível acessar o Enterprise Manager do Weblogic com http://localhost:7002/em

### Automatizando o Deployment com o Oracle Visual Builder Studio

Agora vamos automatizar o deployment das implementações SOA para o servidor Weblogic criado na etapa anterior.
Para isto, você precisará de uma instância do **Oracle Visual Builder Studio**.
#
O **Oracle Visual Builder Studio** trabalha por projetos, logo você poderá criar um projeto e incluir os usuários que farão parte na execução deste.
#
Você poderá ter mais informações de operação na sessão REFERÊNCIAS ao final deste documento, procure por **Deploy with Visual Builder Studio**.
#
A seguir, siga os passos para configurar um build automatizado e deployment de suas implementações SOA SUITE para o servidor Weblogic.
#### Configurando uma máquina virtual para BUILD no ORACLE SOA SUITE
#
Antes de iniciar as configurações, você precisará configurar uma máquina virtual que atenda as necessidades de compilar um projeto **Oracle SOA SUITE**, para isto, você precisará selecionar as ferramentas corretas para serem instaladas nesta VM.
#
Clique em **Organization** no menu lateral esquerdo e em **Virtual Machine Templates**:
![vbst-create-template.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-create-template.png?raw=true)

Clique então em "Create Template" para configurar quais ferramentas estarão disponíveis para o build de seus projetos em SOA SUITE.
#
Digite então um nome para seu **Template** e se quiser, opcionalmente, pode descrever sobre este template.
Não esqueça de selecionar qual será a plataforma desta VM. Escolha a opção **Oracle Linux** (até a data deste documento, tínhamos o Oracle Linux 7, porém você poderá selecionar outra opção mais recente.
![vbst-create-template-2.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-create-template-2.png?raw=true)
Aqui, você deverá selecionar todos os componentes importantes para o build de seus projetos. Escolha todas as opções conforme abaixo:
![vbst-config-sw.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-config-sw.png?raw=true)

![vbst-config-details.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-config-details.png?raw=true)

Pronto! Seu TEMPLATE está criado e sua VM será instanciada assim que algum projeto peça por um build. O que será feito na etapa seguinte.

#### Configuração de BUILD de um projeto no **Oracle Visual Builder Studio**

#
Primeiramente, abra um projeto dentro do **Oracle Visual Builder Studio**, no exemplo abaixo, clique em **CRISTIANO HOSHIKAWA PROJ**:

![main-visual-builder-studio.png](https://github.com/hoshikawa2/repo-image/blob/master/main-visual-builder-studio.png?raw=true)

Você verá a página de seu projeto, juntamente com um menu lateral esquerdo com as opções disponíveis e do lado direito seus projetos no repositório de código-fonte e também o time que faz parte deste projeto (usuários do projeto).
![visual-builder-studio-project.png](https://github.com/hoshikawa2/repo-image/blob/master/visual-builder-studio-project.png?raw=true)

Clique na opção BUILD no menu lateral esquerdo para visualizar todas as configurações existentes e em seguida, vamos configurar um novo BUILD de seu projeto. Clique em "Create Job".
![visual-builder-studio-create-job.png](https://github.com/hoshikawa2/repo-image/blob/master/visual-builder-studio-create-job.png?raw=true)

Digite um nome para seu Job e também selecione o Template de VM (criado na sessão anterior):
![vbst-create-job-details.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-create-job-details.png?raw=true)

Agora vamos configurar passo-a-passo o build de seu projeto.
#
A primeira parte da configuração será estabelecer o repositório do projeto Oracle SOA SUITE que deverá ser previamente criado com o código-fonte. Neste documento, estamos trabalhando com o projeto SOAKafkaProducerApp disponível no git deste documento. 
#
Você poderá clonar este projeto para o git de sua instância de **Oracle Visual Builder Studio**.
#
Após clonar o projeto SOAKafkaProducerApp para seu Visual Builder Studio, configure seu BUILD com o nome do repositório git e selecione a opção "Automatically perform build on SCM commit". Confira também se o nome do branch corresponde ao seu projeto git.
![vbst-git.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-git.png?raw=true)

Selecione agora a aba "Parameters". Você deverá criar 3 parâmetros com o menu Combobox "Add Parameters" ao lado direito. Abaixo, os nomes que devem ser criados e também entre parêntesis, o tipo: 

    WEBLOGICUSER: o usuário Weblogic (tipo String)
    WEBLOGICPWD: a senha do seu usuário Weblogic (tipo Secret/Password)
    WEBLOGICURL: a URL de sua instância Weblogic (tipo String)
    
    Uma observação importante é que a URL em WEBLOGICURL deverá ser localhost
    pois será estabelecido um túnel através do Servidor Bastion

![vbst-parameters.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-parameters.png?raw=true)

Clique agora na aba "Before Build" para configurarmos o túnel SSH do Servidor Bastion para o Servidor Weblogic SOA SUITE.
#
Lembre-se de ter em mãos a chave SSH (Private Key) e preencha o campo correspondente.
#
Juntamente, preencha os dados para a montagem do túnel. 

    Username: opc
    Local Port: 11002
    Remote Host: <IP privado de sua instância SOA SUITE>
    Remote Port: 11002
    SSH Server: <IP público do Servidor Bastion>
    
    Uma observação importante é que a porta 11002 foi previamente configurada para que seja exatamente a porta de acesso para o deployment de seus projetos SOA SUITE

![vbst-tunnel.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-tunnel.png?raw=true)

Clique na aba "Steps" para configurar o BUILD (via Maven) e o DEPLOYMENT (via ANT) de seu projeto SOA SUITE.
#
Preencha da seguinte forma:

    MAVEN
    Goals: compile package
    POM File: SOAKafkaProducerApp/SOAKafkaConsumerPrj/SOA/SCA-INF/pom.xml
    Este é o arquivo pom.xml Maven para a montagem do pacote de seu projeto
    
    ANT
    Targets: sca-package deploy
    Build File: SOAKafkaProducerApp/SOAKafkaConsumerPrj/build.xml
    Este é o arquivo build.xml Ant para o deployment de sua aplicação após o build
    Properties:
        WEBLOGICUSER=${WEBLOGICUSER}
        WEBLOGICPWD=${WEBLOGICPWD}
        WEBLOGICURL=${WEBLOGICURL}    

    Observação importante, a aplicação SOAKafkaProducerApp possui 2 projetos:
    SOAKafkaProducerPrj
    SOAKafkaConsumerPrj
    
    Estamos ilustrando a configuração de um dos projetos. Os mesmos passos devem
    ser executados para o outro projeto.


![vbst-config-step.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-config-step.png?raw=true)

Agora vamos finalizar a configuração do BUILD. Selecione a aba "After Build".
#
Esta etapa é opcional, porém você poderá querer gerar o artefato criado pela etapa anterior, na execução do Maven.
#
Para isto, configure esta etapa para que seja gerado o artefato para download posterior.

![vbst-after-build.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-after-build.png?raw=true)

#### Executando o BUILD no Oracle Visual Builder Studio
Pronto! uma vez configurado, todas as vezes em que houver um **commit** em seu repositório de código (git do Visual Builder Studio), o BUILD será inicializado automaticamente.
#
Você poderá também executar o BUILD manualmente, para isto basta você clicar na opção de BUILD.
#
Experimente executar o BUILD e poderá verificar os Logs gerados

![vbst-log-1.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-log-1.png?raw=true)

![vbst-logs-3.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-logs-3.png?raw=true)

![vbst-logs-2.png](https://github.com/hoshikawa2/repo-image/blob/master/vbst-logs-2.png?raw=true)

### Referências

##### Integrating REST Operations in SOA Composite Applications
https://docs.oracle.com/en/middleware/soa-suite/soa/12.2.1.3/develop/integrating-rest-operations-soa-composite-applications.html#GUID-F11C4900-67D6-4434-83AC-8EB1D52BE238

##### Oracle Streaming Fast Tutorial
https://blogs.oracle.com/developers/getting-started-with-oracle-streaming-service-oss

##### Incorporating Java and Java EE Code in a BPEL Process
https://docs.oracle.com/middleware/1221/soasuite/develop/GUID-7D0BA7EC-65EB-4462-8761-6911D79EFF6A.htm#SOASE500
https://docs.oracle.com/cd/E15586_01/integration.1111/e10224/bp_java.htm#BABDCCED

##### How to Integrate with Java in SOA SUITE
https://redthunder.blog/2016/11/22/teaching-how-to-integrate-with-java-code-in-oracle-soa-composites/

##### Using the Oracle Cloud Infrastructure Streaming Service Adapter with Oracle Integration
https://docs.oracle.com/en/cloud/paas/integration-cloud/stream-service-adapter/prerequisites-creating-connection.html

    cd /u01/app/oracle/middleware/wlserver/server/lib
    
    sudo keytool -importcert -file streaming_us-ashburn-1_oci_oraclecloud_com.crt -keystore DemoTrust.jks -alias “kafka" -storepass DemoTrustKeyStorePassPhrase

##### How to use an SSH Tunnel in Oracle Developer Cloud Service Build Jobs
https://learncodeshare.net/2018/10/31/how-to-use-an-ssh-tunnel-in-oracle-developer-cloud-service-build-jobs/

##### Deploy with Visual Builder Studio
https://docs.oracle.com/en/cloud/paas/developer-cloud/csdcs/deploy-application.html#GUID-5122E3C6-A929-4900-A853-E794A006E52E

    Use este guia para implementar infra-estrutura como código
    
##### Habilitando túneis no Weblogic
https://munzandmore.com/2015/ora/http-instead-t3-weblogic-wlst

##### Weblogic REST API Services
https://docs.oracle.com/middleware/1221/wls/WLRUR/examples.htm#WLRUR193

##### How to Deploy a Single SOA Composite in Oracle JDeveloper
https://docs.oracle.com/middleware/1213/soasuite/develop/GUID-F2B6386E-0F68-4797-96D2-196800394FEF.htm#SOASE158

##### Patching SOA Composite Instances in Oracle 12.2.1
https://blogs.oracle.com/integration/post/patching-soa-composite-instances-in-oracle-1221


    A new Composite Instance Patching feature has been introduced in SOA Suite 12.2.1 which enables you to deliver urgent composite fixes that can be picked up by long running instances.  This feature is part of Oracle Integration Continuous Availability.  Please refer to the Oracle Fusion Middleware Developing SOA Applications with Oracle SOA Suite documentation for additional information.
    
##### How to Configure JDeveloper to deploy into SOACS
https://redthunder.blog/2016/11/22/teaching-how-to-configure-jdveloper-to-deploy-into-soacs/

##### Using the Oracle Cloud Infrastructure Streaming Service Adapter with Oracle Integration
https://docs.oracle.com/en/cloud/paas/integration-cloud/stream-service-adapter/prerequisites-creating-connection.html

##### Weblogic integrated server Demo Identity Keystore and Demo Trust Keystore
http://sanjeev-technology.blogspot.com/2016/07/weblogic-integrated-server-demo.html

