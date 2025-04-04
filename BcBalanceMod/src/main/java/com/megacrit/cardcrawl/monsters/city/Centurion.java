//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.monsters.city;

import bcBalanceMod.BcUtility;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

import java.util.Iterator;

public class Centurion extends AbstractMonster
{
    public static final String ID = "Centurion";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 76;
    private static final int HP_MAX = 80;
    private static final int A_2_HP_MIN = 109;
    private static final int A_2_HP_MAX = 122;
    private static final int SLASH_DMG = 12;
    private static final int FURY_DMG = 6;
    private static final int FURY_HITS = 3;
    private static final int A_2_SLASH_DMG = 14;
    private static final int A_2_FURY_DMG = 7;
    private int slashDmg;
    private int furyDmg;
    private int furyHits;
    private int blockAmount;
    private int BLOCK_AMOUNT = 15;
    private int A_17_BLOCK_AMOUNT = 20;
    private static final byte SLASH = 1;
    private static final byte PROTECT = 2;
    private static final byte FURY = 3;
    private int previousAllyCount;
    
    public Centurion(float x, float y)
    {
        super(NAME, "Centurion", 80, -14.0F, -20.0F, 250.0F, 330.0F, (String) null, x, y);
        
        if (AbstractDungeon.ascensionLevel >= 7)
        {
            setHp(159, 182);
        }
        else
        {
            setHp(148, 160);
        }
        
        if (AbstractDungeon.ascensionLevel >= 17)
        {
            blockAmount = A_17_BLOCK_AMOUNT;
        }
        else
        {
            blockAmount = BLOCK_AMOUNT;
        }
        
        if (AbstractDungeon.ascensionLevel >= 17)
        {
            slashDmg = 20;
            furyDmg = 11;
            furyHits = 3;
        }
        else if (AbstractDungeon.ascensionLevel >= 2)
        {
            slashDmg = 18;
            furyDmg = 9;
            furyHits = 3;
        }
        else
        {
            slashDmg = 12;
            furyDmg = 7;
            furyHits = 3;
        }
        
        //start damaged, so the mystic will immediately heal him
        currentHealth = maxHealth - 55;
        
        damage.add(new DamageInfo(this, slashDmg));
        damage.add(new DamageInfo(this, furyDmg));
        loadAnimation("images/monsters/theCity/tank/skeleton.atlas", "images/monsters/theCity/tank/skeleton.json", 1.0F);
        TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        state.setTimeScale(0.8F);
        previousAllyCount = 0;
        dialogX = -80.0F * Settings.scale;
        dialogY = 50.0F * Settings.scale;
    }
    
    public void update()
    {
        super.update();
        
        int aliveCount = getAliveAllyCount();
        if ((previousAllyCount > aliveCount) &&
            !isDying &&
            !isEscaping &&
            BcUtility.isPlayerInCombat())
        {
            addToBot(new ShoutAction(this, "@NOOO!!!!@"));
        }
        previousAllyCount = aliveCount;
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case SLASH:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "MACE_HIT"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AttackEffect.BLUNT_LIGHT));
                break;
            case PROTECT:
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                AbstractDungeon.actionManager.addToBottom(new GainBlockRandomMonsterAction(this, blockAmount));
                break;
            case FURY:
                for (int i = 0; i < furyHits; ++i)
                {
                    playSfx();
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "MACE_HIT"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AttackEffect.BLUNT_HEAVY));
                }
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if (roll == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1A"));
        }
        else if (roll == 1)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1B"));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1C"));
        }
        
    }
    
    public void changeState(String key)
    {
        byte var3 = -1;
        switch (key.hashCode())
        {
            case 595384490:
                if (key.equals("MACE_HIT"))
                {
                    var3 = 0;
                }
            default:
                switch (var3)
                {
                    case 0:
                        state.setAnimation(0, "Attack", false);
                        state.addAnimation(0, "Idle", true, 0.0F);
                    default:
                }
        }
    }
    
    int getAliveAllyCount()
    {
        int aliveCount = 0;
        
        if (BcUtility.isPlayerInCombat())
        {
            AbstractMonster m;
            Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
            while (var3.hasNext())
            {
                m = (AbstractMonster) var3.next();
                if (!m.isDying && !m.isEscaping)
                {
                    ++aliveCount;
                }
            }
        }
        
        return aliveCount;
    }
    
    protected void getMove(int num)
    {
        int aliveCount = getAliveAllyCount();
        
        //always does big attack when mystic is dead
        if (aliveCount == 1)
        {
            setMove(FURY, Intent.ATTACK, (damage.get(1)).base, furyHits, true);
        }
        else if (num >= 65 && !lastTwoMoves(PROTECT))
        {
            setMove(PROTECT, Intent.DEFEND);
        }
        else if (!lastTwoMoves(SLASH))
        {
            setMove(SLASH, Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
        }
        else
        {
            setMove(PROTECT, Intent.DEFEND);
        }
    }
    
    public void damage(DamageInfo info)
    {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.setTimeScale(0.8F);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
        
    }
    
    public void die()
    {
        state.setTimeScale(0.1F);
        useShakeAnimation(5.0F);
        super.die();
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Centurion");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
