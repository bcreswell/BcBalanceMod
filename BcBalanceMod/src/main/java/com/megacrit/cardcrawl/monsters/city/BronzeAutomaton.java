package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;

import java.util.Iterator;

public class BronzeAutomaton extends AbstractMonster
{
    public static final String ID = "BronzeAutomaton";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    private static final String[] MOVES;
    private static final int HP = 300;
    private static final int A_2_HP = 320;
    private static final byte FLAIL = 1;
    private static final byte HYPER_BEAM = 2;
    private static final byte STUNNED = 3;
    private static final byte SPAWN_ORBS = 4;
    private static final byte BOOST = 5;
    private static final String BEAM_NAME;
    private static final int FLAIL_DMG = 7;
    private static final int BEAM_DMG = 45;
    private static final int A_2_FLAIL_DMG = 8;
    private static final int A_2_BEAM_DMG = 50;
    private int flailDmg;
    private int beamDmg;
    private static final int BLOCK_AMT = 9;
    private static final int STR_AMT = 3;
    private static final int A_2_BLOCK_AMT = 12;
    private static final int A_2_STR_AMT = 4;
    private int strAmt;
    private int blockAmt;
    private int numTurns = 0;
    private boolean firstTurn = true;
    int orbBufferCount;
    
    public BronzeAutomaton()
    {
        super(NAME, "BronzeAutomaton", 300, 0.0F, -30.0F, 270.0F, 400.0F, (String) null, -50.0F, 20.0F);
        loadAnimation("images/monsters/theCity/automaton/skeleton.atlas", "images/monsters/theCity/automaton/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        type = AbstractMonster.EnemyType.BOSS;
        dialogX = -100.0F * Settings.scale;
        dialogY = 10.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 9)
        {
            setHp(320);
            blockAmt = 12;
        }
        else
        {
            setHp(300);
            blockAmt = 9;
        }
        
        flailDmg = 7;
        beamDmg = 45;
        strAmt = 3;
        orbBufferCount = 0;
        
        if (AbstractDungeon.ascensionLevel >= 4)
        {
            flailDmg = 8;
            beamDmg = 50;
            strAmt = 4;
        }
        
        if (AbstractDungeon.ascensionLevel >= 19)
        {
            orbBufferCount = 2;
        }
        
        damage.add(new DamageInfo(this, flailDmg));
        damage.add(new DamageInfo(this, beamDmg));
    }
    
    public void usePreBattleAction()
    {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, 3)));
        UnlockTracker.markBossAsSeen("AUTOMATON");
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case 1:
                addToBot(new AnimateFastAttackAction(this));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
            case 2:
                addToBot(new VFXAction(new LaserBeamEffect(hb.cX, hb.cY + 60.0F * Settings.scale), 1.5F));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AbstractGameAction.AttackEffect.NONE));
                break;
            case 3:
                addToBot(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.STUNNED));
                break;
            case 4:
                if (MathUtils.randomBoolean())
                {
                    addToBot(new SFXAction("AUTOMATON_ORB_SPAWN", MathUtils.random(-0.1F, 0.1F)));
                }
                else
                {
                    addToBot(new SFXAction("MONSTER_AUTOMATON_SUMMON", MathUtils.random(-0.1F, 0.1F)));
                }
                BronzeOrb orb1 = new BronzeOrb(-300.0F, 200.0F, 0);
                addToBot(new SpawnMonsterAction(orb1, true));
                if (orbBufferCount > 0)
                {
                    addToBot(new BcApplyPowerAction(orb1, orb1, new BufferPower(orb1, orbBufferCount)));
                }
                
                if (MathUtils.randomBoolean())
                {
                    addToBot(new SFXAction("AUTOMATON_ORB_SPAWN", MathUtils.random(-0.1F, 0.1F)));
                }
                else
                {
                    addToBot(new SFXAction("MONSTER_AUTOMATON_SUMMON", MathUtils.random(-0.1F, 0.1F)));
                }
                
                BronzeOrb orb2 = new BronzeOrb(200.0F, 130.0F, 0);
                addToBot(new SpawnMonsterAction(orb2, true));
                if (orbBufferCount > 0)
                {
                    addToBot(new BcApplyPowerAction(orb2, orb2, new BufferPower(orb2, orbBufferCount)));
                }
                break;
            case 5:
                addToBot(new GainBlockAction(this, this, blockAmt));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strAmt), strAmt));
        }
        
        addToBot(new RollMoveAction(this));
    }
    
    protected void getMove(int num)
    {
        if (firstTurn)
        {
            setMove((byte) 4, AbstractMonster.Intent.UNKNOWN);
            firstTurn = false;
        }
        else if (numTurns == 4)
        {
            setMove(BEAM_NAME, (byte) 2, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(1)).base);
            numTurns = 0;
        }
        else if (lastMove((byte) 2))
        {
            if (AbstractDungeon.ascensionLevel >= 19)
            {
                setMove((byte) 5, AbstractMonster.Intent.DEFEND_BUFF);
            }
            else
            {
                setMove((byte) 3, AbstractMonster.Intent.STUN);
            }
        }
        else
        {
            if (!lastMove((byte) 3) && !lastMove((byte) 5) && !lastMove((byte) 4))
            {
                setMove((byte) 5, AbstractMonster.Intent.DEFEND_BUFF);
            }
            else
            {
                setMove((byte) 1, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(0)).base, 2, true);
            }
            
            ++numTurns;
        }
    }
    
    public void die()
    {
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        onBossVictoryLogic();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        
        while (var1.hasNext())
        {
            AbstractMonster m = (AbstractMonster) var1.next();
            if (!m.isDead && !m.isDying)
            {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
            }
        }
        
        UnlockTracker.hardUnlockOverride("AUTOMATON");
        UnlockTracker.unlockAchievement("AUTOMATON");
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BronzeAutomaton");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        BEAM_NAME = MOVES[0];
    }
}
