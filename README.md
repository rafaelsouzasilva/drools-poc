# drools-poc

Este projeto é uma poc com Drools.

Obs.: O projeto e o readme será melhorado em breve.

Nele temos a implementação de uma simples API, que com base em três valores `score`, `requestedAmount` e `vehicleValue` definimos o perfil de um cliente que pode ser `PERFIL_1` ou `PERFIL_2`.

Para realizar essa implementação foi necessário a configuração do Drools no projeto, onde adicionamos as dependências do Drools:

```
implementation("org.drools:drools-core:7.0.0.Final")
implementation("org.drools:drools-compiler:7.0.0.Final")
implementation("org.drools:drools-decisiontables:7.0.0.Final")
```

Realizamos também a configuração do `kieContainer`, que basicamente carrega os arquivos com regras escritas em Drools, arquivos `drl` (podemos ver um pouco mais sobre a arquitetura do Drools [nessa página](https://www.tutorialspoint.com/drools/drools_quick_guide.htm)):

```
@Configuration
class DroolsConfiguration {
    private val kieServices: KieServices = KieServices.Factory.get()

    @Bean
    fun getKieContainer(): KieContainer? {
        val kieFileSystem: KieFileSystem = kieServices.newKieFileSystem()
        kieFileSystem.write(newClassPathResource("DroolsPoc.drl"))
        val kb: KieBuilder = kieServices.newKieBuilder(kieFileSystem)
        kb.buildAll()
        val kieModule: KieModule = kb.getKieModule()
        return kieServices.newKieContainer(kieModule.getReleaseId())
    }
}
```

E para finalizar escrevemos as regras que definem o perfil dentro do arquivo Drools `drl`.
Repare que precisamos criar um arquivo "dto" `DroolsVariables`, para conseguir fazer utilização das variáveis dentro do arquivo Drools.

```
import com.poc.drools.DroolsVariables

rule "Defines PERFIL_2 for score > 50 or requestedAmount > 10000 or vehicleValue > 20000"
    when
        droolsVariables : DroolsVariables(score > 50 || requestedAmount > 10000 || vehicleValue > 20000);
    then
        droolsVariables.defineResult("PERFIL_2");
end;

rule "Defines PERFIL_1 for score < 50 e requestedAmount < 10000 e vehicleValue < 20000"
    when
        droolsVariables : DroolsVariables(score < 50 && requestedAmount < 10000 && vehicleValue < 20000);
    then
        droolsVariables.defineResult("PERFIL_1");
end;
```

Podemos testar esse projeto pelo controller que foi implementado `DroolsController`, que está mapeado na rota `/drools`.

![perfil_1](./perfil1.png?raw=true)

![perfil_2](./perfil2.png?raw=true)