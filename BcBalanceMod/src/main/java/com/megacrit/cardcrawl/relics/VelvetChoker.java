package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VelvetChoker extends AbstractRelic
{
    public static final String ID = "Velvet Choker";
    private static final int PLAY_LIMIT = 7;
    
    public VelvetChoker()
    {
        super("Velvet Choker", "redChoker.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return AbstractDungeon.player != null ? this.setDescription(AbstractDungeon.player.chosenClass) : this.setDescription((AbstractPlayer.PlayerClass) null);
    }
    
    private String setDescription(AbstractPlayer.PlayerClass c)
    {
        return this.DESCRIPTIONS[2] + this.DESCRIPTIONS[0] + PLAY_LIMIT + this.DESCRIPTIONS[1];
    }
    
    public void updateDescription(AbstractPlayer.PlayerClass c)
    {
        this.description = this.setDescription(c);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    public void onEquip()
    {
        ++AbstractDungeon.player.energy.energyMaster;
    }
    
    public void onUnequip()
    {
        --AbstractDungeon.player.energy.energyMaster;
    }
    
    public void atBattleStart()
    {
        this.counter = 0;
    }
    
    public void atTurnStart()
    {
        this.counter = 0;
    }
    
    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        if (this.counter < PLAY_LIMIT)
        {
            ++this.counter;
            if (this.counter >= PLAY_LIMIT)
            {
                this.flash();
            }
        }
        
    }
    
    public boolean canPlay(AbstractCard card)
    {
        if (this.counter >= PLAY_LIMIT)
        {
            card.cantUseMessage = this.DESCRIPTIONS[3] + PLAY_LIMIT + this.DESCRIPTIONS[1];
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public void onVictory()
    {
        this.counter = -1;
    }
    
    public AbstractRelic makeCopy()
    {
        return new VelvetChoker();
    }
}
