package be.apb.gfddpp.common.medicationscheme.status;

public enum MSStatusCode {
   MEDICATION_SCHEME_SUCCESS(700000),
   MEDICATION_SCHEME_PARTIAL_SUCCESS(700001),
   MEDICATION_SCHEME_ERROR(700002),
   MEDICATION_SCHEME_NO_RESULTS(700010),
   MEDICATION_SCHEME_VALIDATION_ERRORS(700011),
   MEDICATION_SCHEME_UNKNOWN_INSZ(700100),
   MEDICATION_SCHEME_INVALID_INSZ(700101),
   MEDICATION_SCHEME_NO_SCHEME_FOR_INSS(700102),
   MEDICATION_SCHEME_NO_ACCESS(700103),
   MEDICATION_SCHEME_NO_INSZ(700104),
   MEDICATION_SCHEME_INVALID_SUBJECTID_COUNT(700110),
   MEDICATION_SCHEME_PERSON_INFORMATION_SPECIFIED(700111),
   MEDICATION_SCHEME_ORGANIZATION_INFORMATION_SPECIFIED(700112),
   MEDICATION_SCHEME_NO_DATA(700113),
   MEDICATION_SCHEME_BREAK_THE_GLASS_SPECIFIED(700114),
   MEDICATION_SCHEME_REQUEST_NO_BUSINESSDATA(700115),
   MEDICATION_SCHEME_SEARCHCRITERIA_SPECIFIED(700116),
   MEDICATION_SCHEME_INVALID_PAGINATION_INDEX(700117),
   MEDICATION_SCHEME_INVALID_FETCH_DATA_URI(700118),
   MEDICATION_SCHEME_VERSION_SPECIFIED(700119),
   MEDICATION_SCHEME_NO_DATAENTRIES(700120),
   MEDICATION_SCHEME_NO_DATA_ENTRY_REFERENCE(700121),
   MEDICATION_SCHEME_NO_AVAILABILITY_STATUS(700122),
   MEDICATION_SCHEME_INVALID_AVAILABILITY_STATUS(700123),
   MEDICATION_SCHEME_NO_BUSINESSDATA(700124),
   MEDICATION_SCHEME_INVALID_STORE_DATA_URI(700125),
   MEDICATION_SCHEME_SUBJECT_ID_MISMATCH(700126),
   MEDICATION_SCHEME_NO_FORMAT_CODE(700127),
   MEDICATION_SCHEME_INVALID_FORMAT_CODE(700128),
   MEDICATION_SCHEME_UNPARSEABLE_BUSINESSDATA(700129),
   MEDICATION_SCHEME_INVALID_BUSINESSDATA(700130),
   MEDICATION_SCHEME_MULTIPLE_NODE_VERSIONS(700131);

   private int code;

   private MSStatusCode(int code) {
      this.code = code;
   }

   public int getCode() {
      return this.code;
   }

   public static MSStatusCode fromCode(int code) {
      MSStatusCode[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         MSStatusCode value = var1[var3];
         if (value.getCode() == code) {
            return value;
         }
      }

      throw new IllegalArgumentException("Unknown MSStatusCode for code: " + code);
   }
}
