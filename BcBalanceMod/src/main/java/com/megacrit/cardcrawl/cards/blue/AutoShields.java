//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AutoShields extends BcSkillCardBase
{
    public static final String ID = "Auto Shields";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/auto_shields";
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
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 12 : 16;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "If you have no Block, gain !B! Block.";
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        return (AbstractDungeon.player.currentBlock == 0);
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (AbstractDungeon.player.currentBlock == 0)
        {
            this.addToBot(new GainBlockAction(player, player, this.block));
        }
    }
}