package lt.kslipaitis.osrs.inventory;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum ItemTemplate {
  //    UNKNOWN(pathToTemplate),
  EMPTY("empty"), SILVER_INGOT("silver-ingot"), //    EMERALD_NECKLACE("emerald-necklace"),
  EMERALD("emerald"), ANCHOVIES("anchovies"), OAK_LOG("oak-log"), YEW_LOG("yew-log"), RUBY(
      "ruby"), SAPPHIRE(
      "sapphire"), GOLD_BAR("gold-bar"), RAW_TUNA("raw-tuna"), RAW_SWORDFISH(
      "raw-swordfish"), SWORDFISH(
      "swordfish"), IRON_ORE("iron-ore");


  private final String templateName;

  ItemTemplate(String templateName) {
    this.templateName = templateName;
  }

  public String getTemplateName() {
    return templateName;
  }
}
