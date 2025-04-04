//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Premeditated extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Premeditated");
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Shiv();
    }
    
    @Override
    public String getDisplayName()
    {
        return "Premeditated";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/premeditated.png";
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 4;
    }
    
    public int getDiscardLimit()
    {
        return 10;
    }
    
    public int getBlockPerDiscard()
    {
        return 0;
    }
    
    @Override
    public String getBaseDescription()
    {
        String discardLimitString = "any number of cards";
        int discardLimit = getDiscardLimit();
        if (discardLimit < 9)
        {
            discardLimitString = "up to " + discardLimit + " cards";
        }
        
        int blockPerDiscard = getBlockPerDiscard();
        String blockPerDiscardString = "";
        if (blockPerDiscard > 0)
        {
            blockPerDiscardString = "and gain " + blockPerDiscard + " Block ";
        }
        
        if (getMagicNumber() >= 9)
        {
            return "Discard " + discardLimitString + " from your hand. NL Create !M! *Hidden Shiv " + blockPerDiscardString + "for each discard.";
        }
        else
        {
            return "Discard " + discardLimitString + " from your hand. NL Create !M! *Hidden Shivs " + blockPerDiscardString + "for each discard.";
        }
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        if (getDiscardLimit() >= 9)
        {
            int maxDiscardCount = AbstractDungeon.player.hand.size() - 1;
            return "max discards: " + maxDiscardCount;
        }
        else
        {
            return null;
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToTop(new PremeditatedAction(magicNumber, getDiscardLimit(), getBlockPerDiscard()));
    }
}
