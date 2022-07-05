//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class GremlinNob extends AbstractMonster
{
    public static final String ID = "GremlinNob";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 82;
    private static final int HP_MAX = 86;
    private static final int A_2_HP_MIN = 85;
    private static final int A_2_HP_MAX = 90;
    private static final int BASH_DMG = 6;
    private static final int RUSH_DMG = 14;
    private static final int A_2_BASH_DMG = 8;
    private static final int A_2_RUSH_DMG = 16;
    private static final int DEBUFF_AMT = 2;
    private int bashDmg;
    private int rushDmg;
    private static final byte BULL_RUSH = 1;
    private static final byte SKULL_BASH = 2;
    private static final byte BELLOW = 3;
    private static final int ANGRY_LEVEL = 2;
    private boolean usedBellow;
    private boolean canVuln;
    private int vulnerableCount;
    private  int strengthOnEnrage;
    
    public GremlinNob(float x, float y)
    {
        this(x, y, true);
    }
    
    public GremlinNob(float x, float y, boolean setVuln)
    {
        super(NAME, "GremlinNob", 86, -70.0F, -10.0F, 270.0F, 380.0F, (String) null, x, y);
        usedBellow = false;
        intentOffsetX = -30.0F * Settings.scale;
        type = EnemyType.ELITE;
        dialogX = -60.0F * Settings.scale;
        dialogY = 50.0F * Settings.scale;
        
        canVuln = setVuln;
        vulnerableCount = 2;
        
        if (AbstractDungeon.ascensionLevel >= 8)
        {
            setHp(85, 90);
        }
        else
        {
            setHp(82, 86);
        }
        
        if (AbstractDungeon.ascensionLevel >= 3)
        {
            bashDmg = 8;
            rushDmg = 16;
        }
        else
        {
            bashDmg = 6;
            rushDmg = 14;
        }
        
        strengthOnEnrage = 1;
        if (AbstractDungeon.ascensionLevel >= 18)
        {
            strengthOnEnrage = 2;
        }
        
        damage.add(new DamageInfo(this, rushDmg));
        damage.add(new DamageInfo(this, bashDmg));
        loadAnimation("images/monsters/theBottom/nobGremlin/skeleton.atlas", "images/monsters/theBottom/nobGremlin/skeleton.json", 1.0F);
        TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case BULL_RUSH:                
                addToBot(new AnimateSlowAttackAction(this));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AttackEffect.BLUNT_HEAVY));
                break;
            case SKULL_BASH:
                addToBot(new AnimateSlowAttackAction(this));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AttackEffect.BLUNT_HEAVY));
                if (canVuln)
                {    
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, this,
                            new VulnerablePower(AbstractDungeon.player, vulnerableCount, true), vulnerableCount));
                }
                break;
            case BELLOW:
                playSfx();
                addToBot(new TalkAction(this, DIALOG[0], 1.0F, 3.0F));
                addToBot(new ApplyPowerAction(this, this, new AngerPower(this, strengthOnEnrage), strengthOnEnrage));
        }
        
        addToBot(new RollMoveAction(this));
    }
    
    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if (roll == 0)
        {
            addToBot(new SFXAction("VO_GREMLINNOB_1A"));
        }
        else if (roll == 1)
        {
            addToBot(new SFXAction("VO_GREMLINNOB_1B"));
        }
        else
        {
            addToBot(new SFXAction("VO_GREMLINNOB_1C"));
        }
        
    }
    
    protected void getMove(int num)
    {
        if (!usedBellow)
        {
            usedBellow = true;
            setMove(BELLOW, Intent.BUFF);
        }
        else
        {
            if (AbstractDungeon.ascensionLevel >= 18)
            {
                if (!lastMove(SKULL_BASH) && !lastMoveBefore(SKULL_BASH))
                {
                    if (canVuln)
                    {
                        setMove(MOVES[0], SKULL_BASH, Intent.ATTACK_DEBUFF, (damage.get(1)).base);
                    }
                    else
                    {
                        setMove(SKULL_BASH, Intent.ATTACK, (damage.get(1)).base);
                    }
                    
                    return;
                }
                
                if (lastTwoMoves(BULL_RUSH))
                {
                    if (canVuln)
                    {
                        setMove(MOVES[0], SKULL_BASH, Intent.ATTACK_DEBUFF, (damage.get(1)).base);
                    }
                    else
                    {
                        setMove(SKULL_BASH, Intent.ATTACK, (damage.get(1)).base);
                    }
                }
                else
                {
                    setMove(BULL_RUSH, Intent.ATTACK, (damage.get(0)).base);
                }
            }
            else
            {
                if (num < 33)
                {
                    if (canVuln)
                    {
                        setMove(MOVES[0], SKULL_BASH, Intent.ATTACK_DEBUFF, (damage.get(1)).base);
                    }
                    else
                    {
                        setMove(SKULL_BASH, Intent.ATTACK, (damage.get(1)).base);
                    }
                    
                    return;
                }
                
                if (lastTwoMoves(BULL_RUSH))
                {
                    if (canVuln)
                    {
                        setMove(MOVES[0], SKULL_BASH, Intent.ATTACK_DEBUFF, (damage.get(1)).base);
                    }
                    else
                    {
                        setMove(SKULL_BASH, Intent.ATTACK, (damage.get(1)).base);
                    }
                }
                else
                {
                    setMove(BULL_RUSH, Intent.ATTACK, (damage.get(0)).base);
                }
            }
        }
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinNob");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
