package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.watcher.SanctityAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Sanctity extends BcSkillCardBase
{
    public static final String ID = "Sanctity";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/sanctity";
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
        return !upgraded ? 6 : 9;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    public int getMantraAmount()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL If the last card played this combat was a Skill, draw !M! cards NL and gain " + getMantraAmount() + " Mantra.";
    }
    //endregion
    
    boolean wasLastCardASkill(boolean fromUseMethod)
    {
        int offset = fromUseMethod ? 2 : 1;
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() < offset)
        {
            return false;
        }
        
        AbstractCard lastPlayedCard = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - offset);
        return lastPlayedCard.type == AbstractCard.CardType.SKILL;
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return wasLastCardASkill(false);
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, block));
        
        //handling this here instead of an separate action so that it doesn't draw itself
        if (wasLastCardASkill(true))
        {
            addToBot(new VFXAction(new BorderFlashEffect(Color.GOLD, true), 0.1F));
            addToBot(new SFXAction("HEAL_1"));
            addToBot(new VFXAction(new SanctityEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            addToBot(new DrawCardAction(magicNumber));
            addToBot(new BcApplyPowerAction(new MantraPower(AbstractDungeon.player, getMantraAmount())));
        }
    }
}
