package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThirdEyeEffect;

public class ThirdEye extends BcSkillCardBase
{
    public static final String ID = "ThirdEye";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/third_eye";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 7 : 9;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        int scryAmount = BcUtility.getActualScryAmount(getMagicNumber());
        return "Gain !B! Block. NL Scry " + scryAmount + ".";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (player != null)
        {
            addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
        }
        
        int scryAmount = BcUtility.getActualScryAmount(magicNumber);
        
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new ScryAction(scryAmount));
    }
}
