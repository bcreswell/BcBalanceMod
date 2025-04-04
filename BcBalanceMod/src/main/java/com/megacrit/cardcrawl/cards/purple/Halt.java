package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.HaltAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;

public class Halt extends BcSkillCardBase
{
    public static final String ID = "Halt";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/halt";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 3 : 4;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 9 : 14;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        String extraBlock = BcUtility.getModifiedBlockString(magicNumber);
        
        return applyConditionalHighlight(
            isPlayerInStance(WrathStance.STANCE_ID),
            "Gain !B! Block. NL #gWrath: Gain " + extraBlock + " more.");
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID) &&
            (magicNumber > 0))
        {
            int extraBlock = BcUtility.getModifiedBlock(magicNumber);
            addToBot(new GainBlockAction(player, player, extraBlock));
        }
    }
}
