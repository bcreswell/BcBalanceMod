//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionColor;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class Elixir extends AbstractPotion {
    public static final String POTION_ID = "ElixirPotion";
    private static final PotionStrings potionStrings;

    public Elixir() {
        super(potionStrings.NAME, "ElixirPotion", PotionRarity.UNCOMMON, PotionSize.T, PotionColor.WHITE);
        this.labOutlineColor = Settings.RED_RELIC_COLOR;
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        if ((Settings.language == Settings.GameLanguage.ENG) && (this.potency > 1))
        {
            //todo: figure out best practice for how to do this.
            this.description = "#yExhaust any number of cards in your hand. NL Sacred Bark: Heal for 5.";
        }
        else
        {
            this.description = potionStrings.DESCRIPTIONS[0];
        }

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.EXHAUST.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.EXHAUST.NAMES[0])));
    }

    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            this.addToBot(new ExhaustAction(false, true, true));
        }

        //sacred bark
        if (this.potency > 1)
        {
            AbstractDungeon.player.heal(5);
        }
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new Elixir();
    }

    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString("ElixirPotion");
    }
}