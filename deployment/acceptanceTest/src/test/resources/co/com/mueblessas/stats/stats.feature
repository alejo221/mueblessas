@acceptanceTest
Feature: POST /api/v1/stats - Register stats

  Background:
    * url urlBase
    * configure headers = headers
    * def ValidatorTestUtils = Java.type('co.com.mueblessas.utils.ValidatorTestUtils')

  Scenario: Save stats succefully
    Given path '/stats'
    And request read('valid/addStats.json')
    When method post
    Then status 201
    And match response.success == true
    And match response.message == "Registered"
    And match response.code == 201
    And match response.errors == null
    And match response.timestamp == "#number"
    And match response.data.totalCustomerContacts == "#number"
    And match response.data.complaintReason == "#number"
    And match response.data.warrantyReason == "#number"
    And match response.data.questionReason == "#number"
    And match response.data.purchaseReason == "#number"
    And match response.data.praiseReason == "#number"
    And match response.data.exchangeReason == "#number"
    And match response.data.hash == "#string"
    And match response.data.timestamp == "#number"

  Scenario: Save stats and hash is different from its data
    Given path '/stats'
    And request read('invalid/statsWithInvalidHash.json')
    When method post
    Then status 400
    And match response.success == false
    And match response.message == "The hash is not valid"
    And match response.data == null
    And match response.timestamp == "#number"
    And match response.code == 400
    And match response.errors == null

  Scenario: Save stats and totalCustomerContacts is blank
    Given path '/stats'
    And request read('invalid/statsWithtotalCustomerContactsBlank.json')
    When method post
    Then status 400
    And match response.success == false
    And match response.message == 'Validación fallida'
    And match response.code == 400
    And match response.errors[0].field == 'totalContactoClientes'
    And match response.errors[0].message == 'El totalContactoClientes es obligatorio'

  Scenario: Save stats and complaint reason is blank
    Given path '/stats'
    And request read('invalid/statsWithComplaintReasonBlank.json')
    When method post
    Then status 400
    And match response.success == false
    And match response.message == 'Validación fallida'
    And match response.code == 400
    And match response.errors[0].field == 'motivoReclamo'
    And match response.errors[0].message == 'El motivoReclamo es obligatorio'

  Scenario: Save stats and exchange reason is blank
    Given path '/stats'
    And request read('invalid/statsWithExchangeReasonBlank.json')
    When method post
    Then status 400
    And match response.success == false
    And match response.message == 'Validación fallida'
    And match response.code == 400
    And match response.errors[0].field == 'motivoCambio'
    And match response.errors[0].message == 'El motivoCambio es obligatorio'

  Scenario: Save stats and hash is blank
    Given path '/stats'
    And request read('invalid/statsWithHashBlank.json')
    When method post
    Then status 400
    And match response.success == false
    And match response.message == 'Validación fallida'
    And match response.code == 400
    And match response.errors[0].field == 'hash'
    And match response.errors[0].message == 'El hash es obligatorio'