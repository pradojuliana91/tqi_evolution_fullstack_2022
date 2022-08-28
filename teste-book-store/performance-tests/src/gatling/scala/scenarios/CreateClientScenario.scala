package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import simulations.BaseSimulation

object CreateClientScenario extends BaseSimulation {

  val create: ScenarioBuilder = scenario("Create Client")
    .exec(http("Create Client")
      .post("/clients")
      .basicAuth("bootcamp", "vempratqi")
      .header("Content-type", "application/json")
      .body(StringBody("""{"name":"client 1", "cpf":"11122233300", "address":"rua das alamedas 437", "birthDate":"1923-01-29"}"""))
      .check(status.is(201))
      .check(jsonPath("$.code").saveAs("clientCode"))
    )

    .exec { session =>
      println("Código do Client: --> " + session("clientCode").as[String].trim)
      session
    }
}
