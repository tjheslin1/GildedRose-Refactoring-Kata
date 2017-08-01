package com.gildedrose;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GildedRoseTest {

    @Test
    public void qualityDegradesByOneWhilstDaysLeftOnSellByCounter() throws Exception {
        Item[] items = new Item[]{new Item("item_oneDayLeft", 1, 5)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("item_oneDayLeft");
        assertThat(items[0].sellIn).isEqualTo(0);
        assertThat(items[0].quality).isEqualTo(4);
    }

    @Test
    public void qualityDegradesTwiceAsFastAfterSellByDateHasPassed() throws Exception {
        Item[] items = new Item[]{new Item("item_noDaysLeft", 0, 5)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("item_noDaysLeft");
        assertThat(items[0].sellIn).isEqualTo(-1);
        assertThat(items[0].quality).isEqualTo(3);
    }

    @Test
    public void qualityOfAnItemIsNeverNegative() throws Exception {
        Item[] items = new Item[]{new Item("item_noDaysLeft_noQuality", 0, 0)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("item_noDaysLeft_noQuality");
        assertThat(items[0].sellIn).isEqualTo(-1);
        assertThat(items[0].quality).isEqualTo(0);
    }

    @Test
    public void agedBrieIncreasesInQualityCloserToTheSellInCounterReachingZero() throws Exception {
        Item[] items = new Item[]{new Item("Aged Brie", 5, 10)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("Aged Brie");
        assertThat(items[0].sellIn).isEqualTo(4);
        assertThat(items[0].quality).isEqualTo(11);
    }

    @Test
    public void theQualityOfQualityMaturingItemsNeverIncreaseHigherThanFifty() throws Exception {
        Item[] items = new Item[]{
                new Item("Aged Brie", 5, 50),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 50)
        };

        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("Aged Brie");
        assertThat(items[0].sellIn).isEqualTo(4);
        assertThat(items[0].quality).isEqualTo(50);

        assertThat(items[1].name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(items[1].sellIn).isEqualTo(9);
        assertThat(items[1].quality).isEqualTo(50);
    }

    @Test
    public void legendaryItemSulfurasHasNoChangeToSellInOrQualityOverTime() throws Exception {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 5, 80)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("Sulfuras, Hand of Ragnaros");
        assertThat(items[0].sellIn).isEqualTo(5);
        assertThat(items[0].quality).isEqualTo(80);
    }

    // TODO table test to test all boundaries
    @Test
    public void backstagePassItemsQualityIncreasesByTwoWhenThereAreBetweenTenAndSixDaysLeft() throws Exception {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 40)};

        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(items[0].sellIn).isEqualTo(9);
        assertThat(items[0].quality).isEqualTo(42);
    }

    // TODO table test to test all boundaries
    @Test
    public void backstagePassItemsQualityIncreasesByThreeWhenThereAreBetweenFiveAndZeroDaysLeft() throws Exception {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 4, 40)};

        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(items[0].sellIn).isEqualTo(3);
        assertThat(items[0].quality).isEqualTo(43);
    }

    @Test
    public void backstagePassItemsQualityDropsToZeroWhenTheSellInDateIsPassed() throws Exception {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 40)};

        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(items[0].sellIn).isEqualTo(-1);
        assertThat(items[0].quality).isEqualTo(0);
    }

    @Test
    public void legendaryItemSulfurasAlwaysHasAQualityOfEighty() throws Exception {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 5, 80)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].quality).isEqualTo(80);
    }

    @Test
    public void itemsWhosNameBeginWithConjuredDegradeTwiceAsFast() throws Exception {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 3, 6)};

        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertThat(items[0].name).isEqualTo("Conjured Mana Cake");
        assertThat(items[0].sellIn).isEqualTo(2);
        assertThat(items[0].quality).isEqualTo(4);
    }
}
