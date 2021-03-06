//
//  Generated from FHIR Version 4.0.1-9346c8cc45
//
package org.taktik.icure.fhir.entities.r4.medicinalproductpharmaceutical

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import kotlin.String
import kotlin.collections.List
import org.taktik.icure.fhir.entities.r4.Quantity
import org.taktik.icure.fhir.entities.r4.backboneelement.BackboneElement
import org.taktik.icure.fhir.entities.r4.codeableconcept.CodeableConcept
import org.taktik.icure.fhir.entities.r4.duration.Duration
import org.taktik.icure.fhir.entities.r4.extension.Extension
import org.taktik.icure.fhir.entities.r4.ratio.Ratio

/**
 * The path by which the pharmaceutical product is taken into or makes contact with the body
 *
 * The path by which the pharmaceutical product is taken into or makes contact with the body.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class MedicinalProductPharmaceuticalRouteOfAdministration(
  /**
   * Coded expression for the route
   */
  var code: CodeableConcept,
  override var extension: List<Extension> = listOf(),
  /**
   * The first dose (dose quantity) administered in humans can be specified, for a product under
   * investigation, using a numerical value and its unit of measurement
   */
  var firstDose: Quantity? = null,
  /**
   * Unique id for inter-element referencing
   */
  override var id: String? = null,
  /**
   * The maximum dose per day (maximum dose quantity to be administered in any one 24-h period) that
   * can be administered as per the protocol referenced in the clinical trial authorisation
   */
  var maxDosePerDay: Quantity? = null,
  /**
   * The maximum dose per treatment period that can be administered as per the protocol referenced
   * in the clinical trial authorisation
   */
  var maxDosePerTreatmentPeriod: Ratio? = null,
  /**
   * The maximum single dose that can be administered as per the protocol of a clinical trial can be
   * specified using a numerical value and its unit of measurement
   */
  var maxSingleDose: Quantity? = null,
  /**
   * The maximum treatment period during which an Investigational Medicinal Product can be
   * administered as per the protocol referenced in the clinical trial authorisation
   */
  var maxTreatmentPeriod: Duration? = null,
  override var modifierExtension: List<Extension> = listOf(),
  var targetSpecies: List<MedicinalProductPharmaceuticalRouteOfAdministrationTargetSpecies> =
      listOf()
) : BackboneElement
