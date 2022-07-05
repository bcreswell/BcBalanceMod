package com.megacrit.cardcrawl.monsters.exordium;

import bcBalanceMod.*;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;

import java.sql.*;

public class SlaverRed extends AbstractMonster
{
    public static final String ID = "SlaverRed";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 46;
    private static final int HP_MAX = 50;
    private static final int A_2_HP_MIN = 48;
    private static final int A_2_HP_MAX = 52;
    private static final int STAB_DMG = 13;
    private static final int A_2_STAB_DMG = 14;
    private static final int SCRAPE_DMG = 8;
    private static final int A_2_SCRAPE_DMG = 9;
    private int stabDmg;
    private int scrapeDmg;
    private int VULN_AMT = 1;
    private static final byte STAB = 1;
    private static final byte ENTANGLE = 2;
    private static final byte SCRAPE = 3;
    private static final String SCRAPE_NAME;
    private static final String ENTANGLE_NAME;
    private boolean usedEntangle = false;
    private boolean firstTurn = true;
    int turnNumber = 0;
    int firstEntangleTurn;
    
    public SlaverRed(float x, float y)
    {
        super(NAME, "SlaverRed", 50, 0.0F, 0.0F, 170.0F, 230.0F, (String) null, x, y);
        if (AbstractDungeon.ascensionLevel >= 7)
        {
            //setHp(48, 52);
            setHp(50, 54);
        }
        else
        {
            setHp(46, 50);
        }
        
        if (AbstractDungeon.ascensionLevel >= 2)
        {
            stabDmg = 14;
            scrapeDmg = 9;
        }
        else
        {
            stabDmg = 13;
            scrapeDmg = 8;
        }
        
        if (AbstractDungeon.ascensionLevel >= 17)
        {
            firstEntangleTurn = 2;
        }
        else
        {
            firstEntangleTurn = 3;
        }
        
        damage.add(new DamageInfo(this, stabDmg));
        damage.add(new DamageInfo(this, scrapeDmg));
        loadAnimation("images/monsters/theBottom/redSlaver/skeleton.atlas", "images/monsters/theBottom/redSlaver/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        firstTurn = true;
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case STAB:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case ENTANGLE:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Use Net"));
                if (hb != null && AbstractDungeon.player.hb != null && !Settings.FAST_MODE)
                {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new EntangleEffect(hb.cX - 70.0F * Settings.scale, hb.cY + 10.0F * Settings.scale, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
                }
                
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new EntanglePower(AbstractDungeon.player)));
                usedEntangle = true;
                break;
            case SCRAPE:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                if (AbstractDungeon.ascensionLevel >= 17)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, VULN_AMT + 1, true), VULN_AMT + 1));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, VULN_AMT, true), VULN_AMT));
                }
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if (roll == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERRED_1A"));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERRED_1B"));
        }
        
    }
    
    private void playDeathSfx()
    {
        int roll = MathUtils.random(1);
        if (roll == 0)
        {
            CardCrawlGame.sound.play("VO_SLAVERRED_2A");
        }
        else
        {
            CardCrawlGame.sound.play("VO_SLAVERRED_2B");
        }
        
    }
    
    public void changeState(String stateName)
    {
        if (stateName == "Use Net")
        {
            float tmp = state.getCurrent(0).getTime();
            AnimationState.TrackEntry e = state.setAnimation(0, (String) "idleNoNet", true);
            e.setTime(tmp);
        }
        else if (stateName == "Has Net")
        {
            float tmp = state.getCurrent(0).getTime();
            AnimationState.TrackEntry e = state.setAnimation(0, (String) "idle", true);
            e.setTime(tmp);
        }
    }
    
    protected void getMove(int num)
    {
        turnNumber++;
        boolean canEntangle = !usedEntangle || (AbstractDungeon.ascensionLevel > 17);
        
        if ((turnNumber >= firstEntangleTurn) &&
                    canEntangle &&
                    !BcUtility.lastTwoMoves(this, ENTANGLE))
        {
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Has Net"));
            setMove(ENTANGLE_NAME, ENTANGLE, AbstractMonster.Intent.STRONG_DEBUFF);
        }
        else
        {
            setMove(STAB, AbstractMonster.Intent.ATTACK, stabDmg);
        }

//        if (firstTurn)
//        {
//            firstTurn = false;
//            setMove(STAB, AbstractMonster.Intent.ATTACK, stabDmg);
//        }
//        else if (num >= 75 && !usedEntangle)
//        {
//            setMove(ENTANGLE_NAME, ENTANGLE, AbstractMonster.Intent.STRONG_DEBUFF);
//        }
//        else if (num >= 55 && usedEntangle && !lastTwoMoves(STAB))
//        {
//            setMove(STAB, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
//        }
//        else if (AbstractDungeon.ascensionLevel >= 17)
//        {
//            if (!lastMove((byte) 3))
//            {
//                setMove(SCRAPE_NAME, (byte) 3, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(1)).base);
//            }
//            else
//            {
//                setMove(STAB, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
//            }
//        }
//        else if (!lastTwoMoves((byte) 3))
//        {
//            setMove(SCRAPE_NAME, (byte) 3, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(1)).base);
//        }
//        else
//        {
//            setMove(STAB, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
//        }
    }
    
    public void die()
    {
        super.die();
        playDeathSfx();
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SlaverRed");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        SCRAPE_NAME = MOVES[0];
        ENTANGLE_NAME = MOVES[1];
    }
}
