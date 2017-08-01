package com.gildedrose;

import java.util.List;

import static java.util.Collections.singletonList;

class GildedRose {

    private static final int MAXIMUM_ITEM_QUALITY = 50;
    private static final List<String> LEGENDARY_ITEM_NAMES = singletonList("Sulfuras, Hand of Ragnaros");
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String AGED_BRIE = "Aged Brie";
    private static final int STANDARD_QUALITY_INCREASE = 1;
    private static final String CONJURED_ITEMS_PREFIXED = "Conjured";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (item.name.equals(AGED_BRIE) || item.name.equals(BACKSTAGE_PASS)) {
                increaseQualityOfSpecialItem(item);
            } else {
                if (itemHasQualityRemaining(item) && !itemIsLegendary(item)) {
                    decreaseQuality(item);
                }
            }

            decreaseItemsSellInCounter(item);

            if (itemIsPassedSellIn(item)) {
                if (item.name.equals(AGED_BRIE) && qualityOfItemIsBelowMax(item)) {
                    increaseQuality(item, STANDARD_QUALITY_INCREASE);
                } else {
                    if (item.name.equals(BACKSTAGE_PASS)) {
                        item.quality = 0;
                    } else {
                        if (itemHasQualityRemaining(item) && !itemIsLegendary(item)) {
                            decreaseQuality(item);
                        }
                    }
                }
            }
        }
    }

    private void increaseQualityOfSpecialItem(Item item) {
        if (qualityOfItemIsBelowMax(item)) {
            if (item.name.equals(BACKSTAGE_PASS)) {
                if (item.sellIn < 6) {
                    if (qualityOfItemIsBelowMax(item)) {
                        increaseQuality(item, 3);
                    }
                } else if (item.sellIn < 11) {
                    if (qualityOfItemIsBelowMax(item)) {
                        increaseQuality(item, 2);
                    }
                }
            } else {
                increaseQuality(item, STANDARD_QUALITY_INCREASE);
            }
        }
    }

    private boolean itemHasQualityRemaining(Item item) {
        return item.quality > 0;
    }

    private boolean itemIsPassedSellIn(Item item) {
        return item.sellIn < 0;
    }

    private boolean itemIsLegendary(Item item) {
        return LEGENDARY_ITEM_NAMES.stream()
                .anyMatch(legendary -> item.name.equals(legendary));
    }

    private void decreaseItemsSellInCounter(Item item) {
        if (!itemIsLegendary(item)) {
            item.sellIn = item.sellIn - 1;
        }
    }

    private boolean qualityOfItemIsBelowMax(Item item) {
        return item.quality < MAXIMUM_ITEM_QUALITY;
    }

    private void increaseQuality(Item item, int increase) {
        item.quality = item.quality + increase;
    }

    private void decreaseQuality(Item item) {
        if (item.name.startsWith(CONJURED_ITEMS_PREFIXED)) {
            item.quality = item.quality - 2;
        } else {
            item.quality = item.quality - 1;
        }
    }
}