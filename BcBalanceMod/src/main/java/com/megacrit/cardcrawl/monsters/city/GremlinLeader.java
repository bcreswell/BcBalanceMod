package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;
import java.util.Iterator;

public class GremlinLeader extends AbstractMonster
{
    public static final String ID = "GremlinLeader";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 140;
    private static final int HP_MAX = 148;
    private static final int A_2_HP_MIN = 182;
    private static final int A_2_HP_MAX = 198;
    public static final String ENC_NAME = "Gremlin Leader Combat";
    public AbstractMonster[] gremlins = new AbstractMonster[3];
    public static final float[] POSX;
    public static final float[] POSY;
    private static final String RALLY_NAME;
    private static final byte RALLY = 2;
    private static final byte ENCOURAGE = 3;
    private static final byte STAB = 4;
    private static final int STR_AMT = 3;
    private static final int BLOCK_AMT = 6;
    private static final int A_2_STR_AMT = 4;
    private static final int A_18_STR_AMT = 5;
    private static final int A_18_BLOCK_AMT = 10;
    // gremlin count < SummonThreshold -> summon more
    private static final int SummonThreshold = 2;
    private int strAmt;
    private int blockAmt;
    private int STAB_DMG = 6;
    private int STAB_AMT = 3;
    private int minionsPerSummon = 2;
    private int previousGremlinCount = -1;
    private int startingReinforcments = -1;
    boolean firstTurn = true;
    byte previousMove = -1;
    
    public GremlinLeader()
    {
        super(NAME, "GremlinLeader", 148, 0.0F, -15.0F, 200.0F, 310.0F, (String) null, 35.0F, 0.0F);
        this.type = AbstractMonster.EnemyType.ELITE;
        this.loadAnimation("images/monsters/theCity/gremlinleader/skeleton.atlas", "images/monsters/theCity/gremlinleader/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, (String) "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        if (AbstractDungeon.ascensionLevel >= 8)
        {
            this.setHp(A_2_HP_MIN, A_2_HP_MAX);
        }
        else
        {
            this.setHp(HP_MIN, HP_MAX);
        }
        
        if (AbstractDungeon.ascensionLevel >= 18)
        {
            strAmt = 4;
            blockAmt = 15;
            minionsPerSummon = 3;
            startingReinforcments = 16;
        }
        else if (AbstractDungeon.ascensionLevel >= 3)
        {
            strAmt = 3;
            blockAmt = 10;
            minionsPerSummon = 2;
            startingReinforcments = 12;
        }
        else
        {
            strAmt = 2;
            blockAmt = 5;
            minionsPerSummon = 2;
            startingReinforcments = 12;
        }
        
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, this.STAB_DMG));
    }
    
    public void update()
    {
        super.update();
        
        int gremlinCount = getGremlinCount();
        if (previousGremlinCount != gremlinCount)
        {
            previousGremlinCount = gremlinCount;
            if (!isDying && !isDead)
            {
                rollMove();
            }
        }
    }
    
    public void usePreBattleAction()
    {
        this.gremlins[0] = (AbstractMonster) AbstractDungeon.getMonsters().monsters.get(0);
        this.gremlins[1] = (AbstractMonster) AbstractDungeon.getMonsters().monsters.get(1);
        this.gremlins[2] = null;
        previousGremlinCount = 0;
        for (int i = 0; i < gremlins.length; ++i)
        {
            AbstractMonster gremlin = gremlins[i];
            if (gremlin != null)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(gremlin, gremlin, new MinionPower(this)));
                previousGremlinCount++;
            }
        }
        
        addToBot(new ApplyPowerAction(this, this, new ReinforcementsPower(this, startingReinforcments), startingReinforcments));
    }
    
    public void takeTurn()
    {
        firstTurn = false;
        previousMove = this.nextMove;
label23:
        switch (this.nextMove)
        {
            case RALLY:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CALL"));
                
                int reinforcementsRemaining = getReinforcementsRemaining();
                int toSummonCount = 3 - getGremlinCount();
                toSummonCount = Math.min(Math.min(toSummonCount, minionsPerSummon), reinforcementsRemaining);
                for (int i = 0; i < toSummonCount; i++)
                {
                    addToBot(new SummonGremlinAction(gremlins));
                }
                addToBot(new ReducePowerAction(this, this, ReinforcementsPower.POWER_ID, toSummonCount));
                break;
            case ENCOURAGE:
                addToBot(new ShoutAction(this, this.getEncourageQuote()));
                Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
                
                while (true)
                {
                    if (!var1.hasNext())
                    {
                        break label23;
                    }
                    
                    AbstractMonster m = (AbstractMonster) var1.next();
                    if (m == this)
                    {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, this.strAmt), this.strAmt));
                    }
                    else if (!m.isDying)
                    {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, this.strAmt), this.strAmt));
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, this.blockAmt));
                    }
                }
            case STAB:
                addToBot(new ChangeStateAction(this, "ATTACK"));
                addToBot(new WaitAction(0.5F));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    private String getEncourageQuote()
    {
        ArrayList<String> list = new ArrayList();
        list.add(DIALOG[0]);
        list.add(DIALOG[1]);
        list.add(DIALOG[2]);
        return (String) list.get(AbstractDungeon.aiRng.random(0, list.size() - 1));
    }
    
    protected void getMove(int rngRoll0To99)
    {
        //todo: check the ReinforcementsRemaining buff and attack if it's missing
        
        int gremlinCount = getGremlinCount();
        int reinforcementsRemaining = getReinforcementsRemaining();
        
        if ((reinforcementsRemaining > 0) && (gremlinCount < SummonThreshold))
        {
            setMove(RALLY_NAME, RALLY, AbstractMonster.Intent.UNKNOWN);
        }
        else if ((previousMove == ENCOURAGE) || (MathUtils.random(0, 2) == 0))
        {
            setMove(STAB, AbstractMonster.Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
        }
        else
        {
            setMove(ENCOURAGE, AbstractMonster.Intent.DEFEND_BUFF);
        }
        this.createIntent();
    }
    
    int getReinforcementsRemaining()
    {
        //before the reinforcements buff actually is applied to leader
        if (firstTurn)
        {
            return startingReinforcments;
        }
        
        if (!isDying && !isDead)
        {
            //really wish i had c#'s 'as' keyword here
            AbstractPower power = getPower(ReinforcementsPower.POWER_ID);
            
            if (power != null)
            {
                return power.amount;
            }
        }
        
        return 0;
    }
    
    int getGremlinCount()
    {
        int count = 0;
        
        for (AbstractMonster gremlin : AbstractDungeon.getMonsters().monsters)
        {
            if ((gremlin != null) && (gremlin != this) && (!gremlin.isDying))
            {
                count++;
            }
        }
        
        return count;
    }
    
    public void changeState(String stateName)
    {
        byte var3 = -1;
        switch (stateName.hashCode())
        {
            case 2060894:
                if (stateName.equals("CALL"))
                {
                    var3 = 1;
                }
                break;
            case 1941037640:
                if (stateName.equals("ATTACK"))
                {
                    var3 = 0;
                }
        }
        
        switch (var3)
        {
            case 0:
                this.state.setAnimation(0, (String) "Attack", false);
                this.state.addAnimation(0, (String) "Idle", true, 0.0F);
                break;
            case 1:
                this.state.setAnimation(0, (String) "Call", false);
                this.state.addAnimation(0, (String) "Idle", true, 0.0F);
        }
    }
    
    public void damage(DamageInfo info)
    {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0)
        {
            this.state.setAnimation(0, (String) "Hit", false);
            this.state.addAnimation(0, (String) "Idle", true, 0.0F);
        }
        
    }
    
    public void die()
    {
        super.die();
        boolean first = true;
        Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        
        AbstractMonster m;
        while (var2.hasNext())
        {
            m = (AbstractMonster) var2.next();
            if (!m.isDying)
            {
                if (first)
                {
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(m, DIALOG[3], 0.5F, 1.2F));
                    first = false;
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(m, DIALOG[4], 0.5F, 1.2F));
                }
            }
        }
        
        var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        
        while (var2.hasNext())
        {
            m = (AbstractMonster) var2.next();
            if (!m.isDying)
            {
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
            }
        }
        
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinLeader");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        POSX = new float[]{-366.0F, -170.0F, -532.0F};
        POSY = new float[]{-4.0F, 6.0F, 0.0F};
        RALLY_NAME = MOVES[0];
    }
}
