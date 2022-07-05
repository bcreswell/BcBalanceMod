package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class BookOfStabbing extends AbstractMonster
{
    public static final String ID = "BookOfStabbing";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 160;
    private static final int HP_MAX = 164;
    private static final int A_2_HP_MIN = 168;
    private static final int A_2_HP_MAX = 172;
    private static final int STAB_DAMAGE = 6;
    private static final int BIG_STAB_DAMAGE = 21;
    private static final int A_2_STAB_DAMAGE = 7;
    private static final int A_2_BIG_STAB_DAMAGE = 24;
    private int stabDmg;
    private int bigStabDmg;
    private static final byte MULTI_STAB = 1;
    private static final byte BIG_STAB = 2;
    private int stabCount = 1;
    int strOnBigStab = 0;
    
    public BookOfStabbing()
    {
        super(NAME, "BookOfStabbing", 164, 0.0F, -10.0F, 320.0F, 420.0F, (String) null, 0.0F, 5.0F);
        loadAnimation("images/monsters/theCity/stabBook/skeleton.atlas", "images/monsters/theCity/stabBook/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        e.setTimeScale(0.8F);
        type = AbstractMonster.EnemyType.ELITE;
        dialogX = -70.0F * Settings.scale;
        dialogY = 50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 8)
        {
            setHp(168, 172);
        }
        else
        {
            setHp(160, 164);
        }
        
        stabDmg = 5;
        bigStabDmg = 15;
        strOnBigStab = 0;
        
        if (AbstractDungeon.ascensionLevel >= 3)
        {
            stabDmg = 6;
            bigStabDmg = 20;
        }
        
        if (AbstractDungeon.ascensionLevel >= 18)
        {
            strOnBigStab = 1;
        }
        
        damage.add(new DamageInfo(this, stabDmg));
        damage.add(new DamageInfo(this, bigStabDmg));
    }
    
    public void usePreBattleAction()
    {
        addToBot(new ApplyPowerAction(this, this, new PainfulStabsPower(this)));
    }
    
    public void takeTurn()
    {
label16:
        switch (nextMove)
        {
            case MULTI_STAB:
                addToBot(new ChangeStateAction(this, "ATTACK"));
                addToBot(new WaitAction(0.5F));
                int i = 0;
                
                while (true)
                {
                    if (i >= stabCount)
                    {
                        break label16;
                    }
                    
                    addToBot(new SFXAction("MONSTER_BOOK_STAB_" + MathUtils.random(0, 3)));
                    addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL, false, true));
                    ++i;
                }
            case BIG_STAB:
                DamageInfo bigAttackDmgInfo = damage.get(1);
                addToBot(new ChangeStateAction(this, "ATTACK_2"));
                addToBot(new WaitAction(0.5F));
                addToBot(new DamageAction(AbstractDungeon.player, bigAttackDmgInfo, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                if (strOnBigStab > 0)
                {
                    addToBot(new BcApplyPowerAction(this, this, new StrengthPower(this, strOnBigStab)));
                }
        }
        
        addToBot(new RollMoveAction(this));
    }
    
    public void changeState(String stateName)
    {
        byte var3 = -1;
        switch (stateName.hashCode())
        {
            case 1321368571:
                if (stateName.equals("ATTACK_2"))
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
                state.setAnimation(0, (String) "Attack", false);
                state.addAnimation(0, (String) "Idle", true, 0.0F);
                break;
            case 1:
                state.setAnimation(0, (String) "Attack_2", false);
                state.addAnimation(0, (String) "Idle", true, 0.0F);
        }
        
    }
    
    public void damage(DamageInfo info)
    {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, (String) "Hit", false);
            state.addAnimation(0, (String) "Idle", true, 0.0F);
        }
    }
    
    protected void getMove(int num)
    {
        if (num < 15)
        {
            if (lastMove(BIG_STAB))
            {
                stabCount++;
                setMove(MULTI_STAB, Intent.ATTACK, damage.get(0).base, stabCount, true);
            }
            else
            {
                if (strOnBigStab > 0)
                {
                    setMove(BIG_STAB, Intent.ATTACK_BUFF, damage.get(1).base);
                }
                else
                {
                    setMove(BIG_STAB, Intent.ATTACK, damage.get(1).base);
                }
            }
        }
        else if (lastTwoMoves(MULTI_STAB))
        {
            if (strOnBigStab > 0)
            {
                setMove(BIG_STAB, Intent.ATTACK_BUFF, damage.get(1).base);
            }
            else
            {
                setMove(BIG_STAB, Intent.ATTACK, damage.get(1).base);
            }
        }
        else
        {
            stabCount++;
            setMove(MULTI_STAB, Intent.ATTACK, damage.get(0).base, stabCount, true);
        }
        
    }
    
    public void die()
    {
        super.die();
        CardCrawlGame.sound.play("STAB_BOOK_DEATH");
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BookOfStabbing");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}