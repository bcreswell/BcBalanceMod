package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class BattleHymnPower extends BcPowerBase
{
    public static final String POWER_ID = "BattleHymn";
    int attacksThisTurn = 0;
    int smitesAvailableThisTurn = 0;
    
    public BattleHymnPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Battle Hymn";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "hymn";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "After you play #b" + BattleHymn.AttacksPerSmite + " Attacks in a turn, create a zero cost Smite. NL NL ";
        
        if (smitesAvailableThisTurn > 0)
        {
            description += "Attacks needed: " + (BattleHymn.AttacksPerSmite - attacksThisTurn) +" NL ";
        }
        
        description += "Smites available: "+ smitesAvailableThisTurn;
        
        return description;
    }
    //endregion
    
    @Override
    public void onInitialApplication()
    {
        smitesAvailableThisTurn = amount;
    }
    
    @Override
    public void onPowerStacked()
    {
        smitesAvailableThisTurn++;
    }
    
    public void atStartOfTurn()
    {
        attacksThisTurn = 0;
        smitesAvailableThisTurn = amount;
        updateDescription();
    }
    
    public void onPlayCard(AbstractCard card, AbstractMonster monster)
    {
        if ((card.type == AbstractCard.CardType.ATTACK) &&
            (smitesAvailableThisTurn > 0))
        {
            attacksThisTurn++;
            
            if (attacksThisTurn == BattleHymn.AttacksPerSmite)
            {
                attacksThisTurn = 0;
                smitesAvailableThisTurn--;
                
                flash();
    
                addToBot(new VFXAction(new FastingEffect(player.hb.cX, player.hb.cY, Color.CHARTREUSE)));
                addToBot(new TrueWaitAction(.6f));
                
                Smite smite = new Smite();
                smite.cost = 0;
                smite.costForTurn = 0;
                smite.isCostModified = true;
    
                addToBot(new MakeTempCardInHandAction(smite, 1, false));
            }
            
            updateDescription();
        }
    }
}
