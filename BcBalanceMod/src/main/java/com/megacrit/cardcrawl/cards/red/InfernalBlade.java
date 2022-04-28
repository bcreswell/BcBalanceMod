//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.*;

public class InfernalBlade extends BcSkillCardBase
{
    public static final String ID = "Infernal Blade";
    public static final Color infernalColor = new Color(1f, 0.1f, 0.1f, 1f);
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/infernal_blade";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 2 : 1;
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        //hp lost
        return 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain 1 Strength. NL Lose !M! HP. NL Create a random upgraded Attack that costs 0 this combat.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, 1), 1));
        addToBot(new LoseHPAction(player, player, magicNumber));
        addToBot(new TrueWaitAction(.4f));
        
        AbstractCard card = null;
        while (card == null)
        {
            card = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
            card.upgrade();
            
            if ((card.exhaust) || (card.cost <= 0)) //-1 == x-cost
            {
                //this is supposed to be a special blade. don't want
                // to make a pommel strike or something that will
                // disappear after only one use.
                //
                // Also X-cost cards and 0 cost cards defeat the purpose
                // of making it free for the rest of combat.
                card = null;
            }
        }
        
        BcUtility.setGlowColor(card ,infernalColor);
        
        card.cost = 0;
        card.costForTurn = 0;
        card.isCostModified = true;
        
        //old version - makes it free just for the turn.
        //c.setCostForTurn(0);
        
        addToBot(new MakeTempCardInHandAction(card, true));
    }
}
