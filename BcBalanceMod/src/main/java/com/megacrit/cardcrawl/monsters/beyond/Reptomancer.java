package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

import java.util.*;

public class Reptomancer extends AbstractMonster
{
    public static final String ID = "Reptomancer";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    private static final int HP_MIN = 180;
    private static final int HP_MAX = 190;
    private static final int A_2_HP_MIN = 190;
    private static final int A_2_HP_MAX = 200;
    private static final int BITE_DMG = 30;
    private static final int A_2_BITE_DMG = 34;
    private static final int SNAKE_STRIKE_DMG = 13;
    private static final int A_2_SNAKE_STRIKE_DMG = 16;
    private static final int DAGGERS_PER_SPAWN = 1;
    private static final int ASC_2_DAGGERS_PER_SPAWN = 2;
    private static final byte SNAKE_STRIKE = 1;
    private static final byte SPAWN_DAGGER = 2;
    private static final byte BIG_BITE = 3;
    private static final byte BUFF_MOVE = 4;
    private static final int BuffAmount = 2;
    public static final float[] POSX;
    public static final float[] POSY;
    private int daggersPerSpawn;
    private AbstractMonster[] daggers = new AbstractMonster[4];
    private boolean firstMove = true;
    boolean buffAll;
    boolean hasBuffed;
    
    public Reptomancer()
    {
        super(NAME, "Reptomancer", AbstractDungeon.monsterHpRng.random(180, 190), 0.0F, -30.0F, 220.0F, 320.0F, (String) null, -20.0F, 10.0F);
        type = AbstractMonster.EnemyType.ELITE;
        loadAnimation("images/monsters/theForest/mage/skeleton.atlas", "images/monsters/theForest/mage/skeleton.json", 1.0F);
        
        if (AbstractDungeon.ascensionLevel >= 8)
        {
            //setHp(190, 200);
            setHp(210, 230);
        }
        else
        {
            setHp(180, 190);
        }
        
        hasBuffed = false;
        if (AbstractDungeon.ascensionLevel >= 18)
        {
            daggersPerSpawn = 2;
            buffAll = true;
        }
        else
        {
            daggersPerSpawn = 1;
            buffAll = false;
        }
        
        if (AbstractDungeon.ascensionLevel >= 3)
        {
            damage.add(new DamageInfo(this, 16));
            damage.add(new DamageInfo(this, 34));
        }
        else
        {
            damage.add(new DamageInfo(this, 13));
            damage.add(new DamageInfo(this, 30));
        }
        
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "Idle", true);
        stateData.setMix("Idle", "Sumon", 0.1F);
        stateData.setMix("Sumon", "Idle", 0.1F);
        stateData.setMix("Hurt", "Idle", 0.1F);
        stateData.setMix("Idle", "Hurt", 0.1F);
        stateData.setMix("Attack", "Idle", 0.1F);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    public void usePreBattleAction()
    {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        
        for (AbstractMonster monster : monsters)
        {
            if (!monster.id.equals(id))
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new MinionPower(this)));
            }
            
            if (monster instanceof SnakeDagger)
            {
                if (daggers[0] == null)
                {
                    daggers[0] = monster;
                }
                else
                {
                    daggers[1] = monster;
                }
            }
        }
    }
    
    void snakeStrikeMove()
    {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.ORANGE.cpy()), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.ORANGE.cpy()), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
    }
    
    void bigBiteMove()
    {
        AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.CHARTREUSE.cpy()), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AbstractGameAction.AttackEffect.NONE));
    }
    
    void spawnDaggerMove()
    {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SUMMON"));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
        int daggersSpawned = 0;
        int i = 0;
        
        while (true)
        {
            if ((daggersSpawned >= daggersPerSpawn) || (i >= daggers.length))
            {
                return;
            }
            
            if (daggers[i] == null || daggers[i].isDeadOrEscaped())
            {
                SnakeDagger daggerToSpawn = new SnakeDagger(POSX[i], POSY[i]);
                daggers[i] = daggerToSpawn;
                int monsterCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(daggerToSpawn, true, monsterCount - 1));
                daggersSpawned++;
            }
            
            i++;
        }
    }
    
    void buffMove()
    {
        if (buffAll) //a18+
        {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
            {
                if (!monster.isDying && !monster.isEscaping)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, this, new StrengthPower(monster, BuffAmount), BuffAmount));
                }
            }
        }
        else if (!this.isDying && !this.isEscaping)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, BuffAmount), BuffAmount));
        }
        hasBuffed = true;
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case SNAKE_STRIKE:
                snakeStrikeMove();
                break;
            case SPAWN_DAGGER:
                spawnDaggerMove();
                break;
            case BIG_BITE:
                bigBiteMove();
                break;
            case BUFF_MOVE:
                buffMove();
                break;
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    private boolean canSpawn()
    {
        int aliveCount = 0;
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
        
        while (var2.hasNext())
        {
            AbstractMonster m = (AbstractMonster) var2.next();
            if (m != this && !m.isDying)
            {
                ++aliveCount;
            }
        }
        
        if (aliveCount > 3)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public void damage(DamageInfo info)
    {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, (String) "Hurt", false);
            state.addAnimation(0, (String) "Idle", true, 0.0F);
        }
    }
    
    public void die()
    {
        super.die();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        
        while (var1.hasNext())
        {
            AbstractMonster m = (AbstractMonster) var1.next();
            if (!m.isDead && !m.isDying)
            {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
            }
        }
    }
    
    protected void getMove(int num)
    {
        if (firstMove)
        {
            firstMove = false;
            setMove(SPAWN_DAGGER, AbstractMonster.Intent.UNKNOWN);
        }
        else if (lastMove(SPAWN_DAGGER) && !hasBuffed)
        {
            setMove(BUFF_MOVE, Intent.BUFF);
        }
        else
        {
            if (num < 33)
            {
                if (!lastMove(SNAKE_STRIKE))
                {
                    setMove(SNAKE_STRIKE, Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(0)).base, 2, true);
                }
                else
                {
                    getMove(AbstractDungeon.aiRng.random(33, 99));
                }
            }
            else if (num < 66)
            {
                if (!lastTwoMoves(SPAWN_DAGGER))
                {
                    if (canSpawn())
                    {
                        setMove(SPAWN_DAGGER, AbstractMonster.Intent.UNKNOWN);
                    }
                    else
                    {
                        setMove(SNAKE_STRIKE, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(0)).base, 2, true);
                    }
                }
                else
                {
                    setMove(SNAKE_STRIKE, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(0)).base, 2, true);
                }
            }
            else if (!lastMove(BIG_BITE))
            {
                setMove(BIG_BITE, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(1)).base);
            }
            else
            {
                getMove(AbstractDungeon.aiRng.random(65));
            }
        }
    }
    
    public void changeState(String key)
    {
        byte var3 = -1;
        switch (key.hashCode())
        {
            case -1837878047:
                if (key.equals("SUMMON"))
                {
                    var3 = 1;
                }
                break;
            case 1941037640:
                if (key.equals("ATTACK"))
                {
                    var3 = 0;
                }
        }
        
        switch (var3)
        {
            case 0:
                state.setAnimation(0, (String) "Attack", false);
                state.addAnimation(0, (String) "Idle", true, 0.0F);
                break;
            case 1:
                state.setAnimation(0, (String) "Sumon", false);
                state.addAnimation(0, (String) "Idle", true, 0.0F);
        }
        
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Reptomancer");
        NAME = monsterStrings.NAME;
        POSX = new float[]{210.0F, -220.0F, 180.0F, -250.0F};
        POSY = new float[]{75.0F, 115.0F, 345.0F, 335.0F};
    }
}
