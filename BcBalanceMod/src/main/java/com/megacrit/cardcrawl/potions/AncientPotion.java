package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AncientPotion extends AbstractPotion
{
    public static final String POTION_ID = "Ancient Potion";
    private static final PotionStrings potionStrings;
    
    public AncientPotion()
    {
        super(potionStrings.NAME, "Ancient Potion", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.FAIRY, AbstractPotion.PotionColor.ANCIENT);
        isThrown = false;
    }
    
    public void initializeData()
    {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + " NL NL (" + ArtifactPower.Footnote + ")";
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.ARTIFACT.NAMES[0]), (String) GameDictionary.keywords.get(GameDictionary.ARTIFACT.NAMES[0])));
    }
    
    public void use(AbstractCreature target)
    {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new ArtifactPower(target, potency), potency));
        }
    }
    
    public int getPotency(int ascensionLevel)
    {
        return 1;
    }
    
    public AbstractPotion makeCopy()
    {
        return new AncientPotion();
    }
    
    static
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("Ancient Potion");
    }
}
