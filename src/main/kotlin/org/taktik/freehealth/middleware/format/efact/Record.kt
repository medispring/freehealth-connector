package org.taktik.freehealth.middleware.format.efact

import com.fasterxml.jackson.annotation.JsonIgnore
import org.taktik.freehealth.middleware.format.efact.segments.RecordOrSegmentDescription
import org.taktik.freehealth.middleware.format.efact.segments.Segment200Description

class Record<T:RecordOrSegmentDescription>(var description: T? = null, var zones: List<Zone> = ArrayList(), var errorDetail: ErrorDetail? = null) {
    override fun toString(): String {
        return description.toString()+":\n"+zones.map { "\t"+it.toString() }.joinToString("\n")
    }
}
