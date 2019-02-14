/*
 *
 * Copyright (C) 2018 Taktik SA
 *
 * This file is part of FreeHealthConnector.
 *
 * FreeHealthConnector is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation.
 *
 * FreeHealthConnector is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with FreeHealthConnector.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.taktik.freehealth.middleware.service

import be.fgov.ehealth.hubservices.core.v2.ConsentType
import org.taktik.connector.business.domain.consent.ConsentMessage
import java.util.*

interface ConsentService {
    fun registerPatientConsent(
        keystoreId: UUID,
        tokenId: UUID,
        passPhrase: String,
        hcpNihii: String,
        hcpSsin: String,
        hcpFirstName: String,
        hcpLastName: String,
        hcpQuality: String,
        patientSsin: String,
        patientFirstName: String,
        patientLastName: String,
        eidCardNumber: String?,
        isiCardNumber: String?
    ): ConsentMessage

    fun getPatientConsent(
        keystoreId: UUID,
        tokenId: UUID,
        passPhrase: String,
        hcpNihii: String,
        hcpSsin: String,
        hcpFirstName: String,
        hcpLastName: String,
        hcpQuality: String,
        patientSsin: String,
        patientFirstName: String,
        patientLastName: String
    ): ConsentMessage

    fun revokePatientConsent(
        keystoreId: UUID,
        tokenId: UUID,
        passPhrase: String,
        hcpNihii: String,
        hcpSsin: String,
        hcpFirstName: String,
        hcpLastName: String,
        hcpQuality: String,
        existingConsent: ConsentType,
        eidCardNumber: String?,
        isiCardNumber: String?
    ): ConsentMessage
}
