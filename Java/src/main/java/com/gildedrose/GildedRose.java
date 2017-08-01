package com.gildedrose;

import java.util.List;

import static java.util.Collections.singletonList;

class GildedRose {

    private static final int MAXIMUM_ITEM_QUALITY = 50;
    private static final List<String> LEGENDARY_ITEM_NAMES = singletonList("Sulfuras, Hand of Ragnaros");

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!item.name.equals("Aged Brie")
                    && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (item.quality > 0) {
                    if (!itemIsLegendary(item)) {
                        decreaseQuality(item);
                    }
                }
            } else {
                if (qualityOfItemIsBelowMax(item)) {
                    increaseQuality(item);

                    if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (item.sellIn < 11) {
                            if (qualityOfItemIsBelowMax(item)) {
                                increaseQuality(item);
                            }
                        }

                        if (item.sellIn < 6) {
                            if (qualityOfItemIsBelowMax(item)) {
                                increaseQuality(item);
                            }
                        }
                    }
                }
            }

            decreaseItemsSellInCounter(item);

            if (item.sellIn < 0) {
                if (!item.name.equals("Aged Brie")) {
                    if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (item.quality > 0) {
                            if (!itemIsLegendary(item)) {
                                decreaseQuality(item);
                            }
                        }
                    } else {
                        item.quality = 0;
                    }
                } else {
                    if (qualityOfItemIsBelowMax(item)) {
                        increaseQuality(item);
                    }
                }
            }
        }
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

    private void increaseQuality(Item item) {
        item.quality = item.quality + 1;
    }

    private void decreaseQuality(Item item) {
        item.quality = item.quality - 1;
    }
}