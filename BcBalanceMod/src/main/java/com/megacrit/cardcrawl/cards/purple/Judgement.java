package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantTextEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class Judgement extends BcSkillCardBase
{
    public static final String ID = "Judgement";
    public static final CardStrings cardStrings;
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Judgement");
    }
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Judgement";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/skill/judgment";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 30 : 40;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "If the enemy has !M! or less HP, set their NL HP to 0.";
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        return validTargetExists();
    }
    
    boolean validTargetExists()
    {
        if (BcUtility.isPlayerInCombat())
        {
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (!monster.isDeadOrEscaped() && (monster.currentHealth <= getMagicNumber()))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean canUse(AbstractPlayer player, AbstractMonster monster)
    {
        if (!super.canUse(player, monster))
        {
            return false;
        }
        else if ((monster != null) && (monster.currentHealth > getMagicNumber()))
        {
            this.cantUseMessage = "Enemy has too much HP.";
            return false;
        }
        
        return true;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (monster != null)
        {
            addToBot(new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY, Color.GOLD.cpy())));
            addToBot(new WaitAction(0.8F));
            addToBot(new VFXAction(new GiantTextEffect(monster.hb.cX, monster.hb.cY)));
        }
        
        addToBot(new JudgementAction(monster, magicNumber));
    }
}
