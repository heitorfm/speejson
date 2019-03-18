[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=heitorfm_speejson&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=heitorfm_speejson) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=heitorfm_speejson&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=heitorfm_speejson)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=heitorfm_speejson&metric=bugs)](https://sonarcloud.io/dashboard?id=heitorfm_speejson) [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=heitorfm_speejson&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=heitorfm_speejson) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=heitorfm_speejson&metric=ncloc)](https://sonarcloud.io/dashboard?id=heitorfm_speejson) 


# SpeeJson <img src="https://heitorfm.github.io/speejson/img/clock.png" align="right" style="height: 64px"/>

#### **SpeeJson is a extreme high throughput java library for Json serialization and deserialization**

<br /><br />

## Easy to use <img src="https://heitorfm.github.io/speejson/img/coder.png" align="right" style="height: 128px"/>



```java
ByteArrayOutputStream os = new ByteArrayOutputStream();

Person person = new Person();
// fullfill properties

SpeeJson speedjson = new SpeeJson();
speedjson.put(os, person);
```


## Fast <img src="https://heitorfm.github.io/speejson/img/timer.png" align="right" style="height: 128px"/>


Around **100%** faster than Jackson. From Java object -> json

```json
{  
       "id":null,
       "name":"Afonso Silva",
       "age":18,
       "birth":1552753131143,
       "taxId":"123345456",
       "email":"email@server.com",
       "salary":1500,
       "active":true,
       "address":null
}
```

### JACKSON => 66567 nanos | 66 micros | 0 millis

### SPEEJSON => 29637 nanos  |  29 micros  |  0 millis
### SPEEJSON => 32478 nanos  |  32 micros  |  0 millis
### SPEEJSON => 33185 nanos  |  33 micros  |  0 millis

# Special Thanks
![JProfiler](https://www.ej-technologies.com/images/product_banners/jprofiler_small.png) This project uses and recomends JProfiler as [Java Profiler](https://www.ej-technologies.com/products/jprofiler/overview.html)


# Licensing

GNU LGPLv3

Permissions of this copyleft license are conditioned on making available complete source code of licensed works and modifications under the same license or the GNU GPLv3. Copyright and license notices must be preserved. Contributors provide an express grant of patent rights. However, a larger work using the licensed work through interfaces provided by the licensed work may be distributed under different terms and without source code for the larger work.


| Permissions     | Conditions                   | Limitations |
|-----------------|------------------------------|-------------|
|<img src="https://heitorfm.github.io/speejson/img/green.png" /> Commercial use  |<img src="https://heitorfm.github.io/speejson/img/blue.png" /> Disclose source              |<img src="https://heitorfm.github.io/speejson/img/red.png" /> Liability   |
|<img src="https://heitorfm.github.io/speejson/img/green.png" /> Distribution    |<img src="https://heitorfm.github.io/speejson/img/blue.png" /> License and copyright notice |<img src="https://heitorfm.github.io/speejson/img/red.png" /> Warranty    |
|<img src="https://heitorfm.github.io/speejson/img/green.png" /> Modification    |<img src="https://heitorfm.github.io/speejson/img/blue.png" /> Same license                 |             |  
|<img src="https://heitorfm.github.io/speejson/img/green.png" /> Patent use      |<img src="https://heitorfm.github.io/speejson/img/blue.png" /> State changes                |             |
|<img src="https://heitorfm.github.io/speejson/img/green.png" /> Private use     |                              |             |

  
