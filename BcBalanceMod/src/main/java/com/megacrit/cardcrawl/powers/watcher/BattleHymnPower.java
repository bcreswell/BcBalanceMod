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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.*;

public class BattleHymnPower extends BcPowerBase
{
    public static final String POWER_ID = "BattleHymn";
    int attacksThisTurn = 0;
    
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
        String description = null;
        if (amount == 1)
        {
            description = "Once per turn: NL If you play #b" + BattleHymn.AttacksPerSmite + " Attacks (not counting Smites), create a zero cost Smite.";
        }
        else
        {
            description = "Once per turn: NL If you play #b" + BattleHymn.AttacksPerSmite + " Attacks (not counting Smites), create " + amount + " zero cost Smites.";
        }
        
        description += " NL NL Non-Smite Attacks played this turn: " + attacksThisTurn;
        
        return description;
    }
    //endregion
    
    public void atStartOfTurn()
    {
        attacksThisTurn = 0;
        updateDescription();
    }
    
    public void onPlayCard(AbstractCard card, AbstractMonster monster)
    {
        if ((card.type == AbstractCard.CardType.ATTACK) &&
                    !card.cardID.equals(Smite.ID))
        {
            attacksThisTurn++;
            
            if (attacksThisTurn == BattleHymn.AttacksPerSmite)
            {
                flash();
    
                addToBot(new VFXAction(new FastingEffect(player.hb.cX, player.hb.cY, Color.CHARTREUSE)));
                addToBot(new TrueWaitAction(.6f));
                
                Smite smite = new Smite();
                smite.cost = 0;
                smite.costForTurn = 0;
                smite.isCostModified = true;
    
                addToBot(new MakeTempCardInHandAction(smite, amount, false));
            }
            
            updateDescription();
        }
    }
}
