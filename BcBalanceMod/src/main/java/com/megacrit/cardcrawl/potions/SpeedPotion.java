package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class SpeedPotion extends AbstractPotion
{
    public static final String POTION_ID = "SpeedPotion";
    private static final PotionStrings potionStrings;
    
    public SpeedPotion()
    {
        super(potionStrings.NAME, "SpeedPotion", AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.S, AbstractPotion.PotionColor.SKILL);
        isThrown = false;
    }
    
    public void initializeData()
    {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.DEXTERITY.NAMES[0]), GameDictionary.keywords.get(GameDictionary.DEXTERITY.NAMES[0])));
    }
    
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractPlayer player = AbstractDungeon.player;
            
            addToBot(new BcApplyPowerAction(new DexterityPower(player, potency)));
            addToBot(new BcApplyPowerAction(new LoseDexterityPower(player, potency)));
        }
    }
    
    public int getPotency(int ascensionLevel)
    {
        return 5;
    }
    
    public AbstractPotion makeCopy()
    {
        return new SpeedPotion();
    }
    
    static
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("SpeedPotion");
    }
}
