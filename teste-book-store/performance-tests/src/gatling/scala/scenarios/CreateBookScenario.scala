package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import simulations.BaseSimulation

object CreateBookScenario extends BaseSimulation {

  val create: ScenarioBuilder = scenario("Create Book")
    .exec(http("Create Book")
      .post("/books")
      .basicAuth("bootcamp", "vempratqi")
      .header("Content-type", "multipart/form-data")
      .formParam("title", "Teste de Software")
      .formParam("authorCode", "2FCCEE18-4928-49EE-BA8D-5611B3CE91F2")
      .formParam("publisher", "Eitora abril de julho")
      .formParam("yearPublication", "1988")
      .formParam("saleValue", "999")
      .formUpload("file", "src/gatling/resources/images/automacao-testes.png")
      .check(status.is(201))
      .check(jsonPath("$.code").saveAs("bookCode"))
    )

    .exec { session =>
      println("Código do Livro: --> " + session("bookCode").as[String].trim)
      session
    }

}
