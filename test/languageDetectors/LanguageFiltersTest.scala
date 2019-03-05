package languageDetectors

import org.scalatest.{FlatSpec, Matchers}

class LanguageFiltersTest extends FlatSpec with Matchers {

  "Persian Filter" should "return true if String contains Persian" in {
    LanguageFilters.persianFilter("سال ۲۰۱۹ با نمایشگاه بین\u200Cالمللی سی ای اس در لاس\u200Cوگاس آغاز شد") should be
    true
  }

  it should "return false if input String contains no Persian" in {
    LanguageFilters.persianFilter("hello") should be
    false
  }

  it should "return true if input String contains Arabic question mark" in {
    LanguageFilters.persianFilter("؟") should be
    true
  }

  it should "return true if input String contains Arabic semi colon" in {
    LanguageFilters.persianFilter("؛") should be
    true
  }

  it should "return true if input String contains Arabic comma" in {
    LanguageFilters.persianFilter("،") should be
    true
  }

  it should "return true if input String contains a mixture of Persian and another language" in {
    LanguageFilters.persianFilter("و هر چه که توجه\u200Cها رو به خودش جلب کنه... چیزیEnglish Text که اصلا ماهیت وگاسه.") should be
    true
  }

  it should "return false if input String is empty" in {
    LanguageFilters.persianFilter("") should be
    false
  }

  it should "return false if input String contains only numbers" in {
    LanguageFilters.persianFilter("123") should be
    false
  }

}
