package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class SteroidPotion extends AbstractPotion
{
    public static final String POTION_ID = "SteroidPotion";
    private static final PotionStrings potionStrings;
    
    public SteroidPotion()
    {
        super(potionStrings.NAME, "SteroidPotion", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.FAIRY, AbstractPotion.PotionColor.STEROID);
        isThrown = false;
    }
    
    public void initializeData()
    {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STRENGTH.NAMES[0]), (String) GameDictionary.keywords.get(GameDictionary.STRENGTH.NAMES[0])));
    }
    
    public void use(AbstractCreature target)
    {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            addToBot(new BcApplyPowerAction(new StrengthPower(target, potency)));
            addToBot(new BcApplyPowerAction(new LoseStrengthPower(target, potency)));
        }
    }
    
    public int getPotency(int ascensionLevel)
    {
        return 5;
    }
    
    public AbstractPotion makeCopy()
    {
        return new SteroidPotion();
    }
    
    static
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("SteroidPotion");
    }
}
