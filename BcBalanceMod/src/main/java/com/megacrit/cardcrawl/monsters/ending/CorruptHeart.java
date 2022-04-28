package com.megacrit.cardcrawl.monsters.ending;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.HeartAnimListener;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BeatOfDeathPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.PainfulStabsPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import org.apache.logging.log4j.*;

import java.util.*;

public class CorruptHeart extends AbstractMonster
{
    //region Variables
    private static final Logger logger = LogManager.getLogger(CorruptHeart.class.getName());
    public static final String ID = "CorruptHeart";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("CorruptHeart");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
    
    static class HeartMoves
    {
        public static final byte Buff = 0;
        public static final byte MultiAttack = 1;
        public static final byte BigAttack = 2;
        public static final byte Debuff = 3;
        public static final byte Summon = 4;
        
        public static String getName(byte move)
        {
            switch (move)
            {
                case Buff:
                    return "Buff";
                case MultiAttack:
                    return "MultiAttack";
                case BigAttack:
                    return "BigAttack";
                case Debuff:
                    return "Debuff";
                case Summon:
                    return "Summon";
            }
            
            return null;
        }
    }
    
    private HeartAnimListener animListener = new HeartAnimListener();
    
    DamageInfo multiAttackInfo;
    DamageInfo bigAttackInfo;
    ArrayList<AbstractMonster> monstersToSummon = new ArrayList<>();
    int multiAttackBase;
    int multiAttackCount;
    int bigAttackBase;
    int turnNumber;
    int cycleNumber;
    int initialBeatOfDeath;
    int damageCap;
    
    
    AbstractPlayer player;
    //endregion
    
    public CorruptHeart()
    {
        super(NAME, "CorruptHeart", 750, 30.0F, -30.0F, 476.0F, 500.0F, (String) null, 120, 0.0F);
        loadAnimation("images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "idle", true);
        e.setTimeScale(1.5F);
        state.addListener(animListener);
        type = AbstractMonster.EnemyType.BOSS;
        player = AbstractDungeon.player;
        
        if (AbstractDungeon.ascensionLevel >= 9)
        {
            //setHp(800);
            setHp(1300);
        }
        else
        {
            //setHp(750);
            setHp(1000);
        }
        
        initialBeatOfDeath = 1;
        damageCap = 300;
        multiAttackBase = 2;
        multiAttackCount = 12;
        bigAttackBase = 20;
        
        if (AbstractDungeon.ascensionLevel >= 4)
        {
            bigAttackBase = 30;
            multiAttackCount = 15;
        }
        
        if (AbstractDungeon.ascensionLevel >= 19)
        {
            initialBeatOfDeath = 2;
            damageCap = 250;
        }
        
        multiAttackInfo = new DamageInfo(this, multiAttackBase);
        bigAttackInfo = new DamageInfo(this, bigAttackBase);
        damage.add(multiAttackInfo);
        damage.add(bigAttackInfo);
        
        //pick which monsters to summon
        int x = -360;
        int y = 20;
        
        int x1 = -250;
        int y1 = 20;
        int x2 = -530;
        int y2 = 0;
        
        if (AbstractDungeon.ascensionLevel < 20)
        {
            int summonVal = AbstractDungeon.aiRng.random(0, 2);
            if (summonVal == 0)
            {
                monstersToSummon.add(new JawWorm(x, y, false));
            }
            else if (summonVal == 1)
            {
                monstersToSummon.add(new AcidSlime_L(x, y));
            }
            else if (summonVal == 2)
            {
                monstersToSummon.add(new SpikeSlime_L(x, y));
            }
        }
        else //ascension 20
        {
            int summonVal = AbstractDungeon.aiRng.random(0, 2);
            if (summonVal == 0)
            {
                monstersToSummon.add(new ShelledParasite(x, y));
            }
            else if (summonVal == 1)
            {
                monstersToSummon.add(new OrbWalker(x, y));
            }
            else if (summonVal == 2)
            {
                monstersToSummon.add(new Spiker(x1, y1));
                monstersToSummon.add(new FungiBeast(x2, y2));
            }
        }
    }
    
    //region Methods
    public void usePreBattleAction()
    {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_ENDING");
        
        addToBot(new ApplyPowerAction(this, this, new InvinciblePower(this, damageCap), damageCap));
    }
    
    //region moves
    public void doBuffMove(boolean showIntent)
    {
        if (showIntent)
        {
            setMove(HeartMoves.Buff, Intent.BUFF);
        }
        else
        {
            addToBot(new NormalizeStrengthAction(this));
            
            addToBot(new VFXAction(new BorderFlashEffect(new Color(0.8F, 0.5F, 1.0F, 1.0F))));
            addToBot(new VFXAction(new HeartBuffEffect(hb.cX, hb.cY)));
            
            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
            
            if (cycleNumber <= 3)
            {
                addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, 2), 2));
            }
            else if (cycleNumber == 5)
            {
                addToBot(new ApplyPowerAction(this, this, new PainfulStabsPower(this)));
            }
            else if (cycleNumber >= 6)
            {
                addToBot(new ApplyPowerAction(this, this, new BeatOfDeathPower(this, 1), 1));
            }
        }
    }
    
    public void doDebuffMove(boolean showIntent)
    {
        if (showIntent)
        {
            setMove(HeartMoves.Debuff, Intent.STRONG_DEBUFF);
        }
        else
        {
            addToBot(new NormalizeStrengthAction(this));
            
            addToBot(new VFXAction(new HeartMegaDebuffEffect()));
            addToBot(new ApplyPowerAction(player, this, new VulnerablePower(player, 2, true), 2));
            addToBot(new ApplyPowerAction(player, this, new FrailPower(player, 2, true), 2));
            addToBot(new ApplyPowerAction(player, this, new WeakPower(player, 2, true), 2));
            
            addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, false, false, (float) Settings.WIDTH * 0.2F, (float) Settings.HEIGHT / 2.0F));
            addToBot(new MakeTempCardInDrawPileAction(new Slimed(), 1, true, false, false, (float) Settings.WIDTH * 0.35F, (float) Settings.HEIGHT / 2.0F));
            addToBot(new MakeTempCardInDrawPileAction(new Wound(), 1, true, false, false, (float) Settings.WIDTH * 0.5F, (float) Settings.HEIGHT / 2.0F));
            addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false, false, (float) Settings.WIDTH * 0.65F, (float) Settings.HEIGHT / 2.0F));
            addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, false, false, (float) Settings.WIDTH * 0.8F, (float) Settings.HEIGHT / 2.0F));
        }
    }
    
    public void doSummonMove(boolean showIntent)
    {
        if (showIntent)
        {
            setMove(HeartMoves.Summon, Intent.UNKNOWN);
        }
        else
        {
            addToBot(new NormalizeStrengthAction(this));
            
            addToBot(new ApplyPowerAction(this, this, new BeatOfDeathPower(this, initialBeatOfDeath), initialBeatOfDeath));
            
            addToBot(new HeartSummonAction(monstersToSummon));
        }
    }
    
    public void doMultiAttackMove(boolean showIntent)
    {
        if (showIntent)
        {
            setMove(HeartMoves.MultiAttack, Intent.ATTACK, multiAttackInfo.base, multiAttackCount, true);
        }
        else
        {
            addToBot(new VFXAction(new BloodShotEffect(hb.cX, hb.cY, player.hb.cX, player.hb.cY, multiAttackCount), 0.6F));
            
            for (int i = 0; i < multiAttackCount; i++)
            {
                addToBot(new DamageAction(player, multiAttackInfo, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
            }
        }
    }
    
    public void doBigAttackMove(boolean showIntent)
    {
        bigAttackInfo.base = bigAttackBase + cycleNumber * 10;
        
        if (showIntent)
        {
            setMove(HeartMoves.BigAttack, Intent.ATTACK, bigAttackInfo.base);
        }
        else
        {
            addToBot(new VFXAction(new ViceCrushEffect(player.hb.cX, player.hb.cY), 0.5F));
            addToBot(new DamageAction(player, bigAttackInfo, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }
    //endregion
    
    @Override
    protected void getMove(int randomVal0To99)
    {
        //starts at 1
        turnNumber++;
        
        //starts at 1
        cycleNumber = ((turnNumber - 1) / 3) + 1;
        
        //either 1, 2, or 3. wraps around.
        int cyclePart = ((turnNumber - 1) % 3) + 1;
        
        if (cyclePart == 1) //special turn
        {
            if (cycleNumber == 1)
            {
                doSummonMove(true);
            }
            else if (cycleNumber == 2)
            {
                doDebuffMove(true);
            }
            else if (cycleNumber == 3)
            {
                doBuffMove(true);
            }
            else if (cycleNumber >= 4)
            {
                doBuffMove(true);
            }
        }
        else if (cyclePart == 2) // first attack turn
        {
            if (randomVal0To99 >= 50)
            {
                doMultiAttackMove(true);
            }
            else
            {
                doBigAttackMove(true);
            }
        }
        else if (cyclePart == 3) // second attack turn
        {
            if (lastMove(HeartMoves.BigAttack))
            {
                doMultiAttackMove(true);
            }
            else if (lastMove(HeartMoves.MultiAttack))
            {
                doBigAttackMove(true);
            }
        }
        
        logger.info("turnNumber: " + turnNumber + ", cycleNumber: " + cycleNumber + ", nextMove: " + HeartMoves.getName(getLastMove()));
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case HeartMoves.Buff:
                doBuffMove(false);
                break;
            case HeartMoves.MultiAttack:
                doMultiAttackMove(false);
                break;
            case HeartMoves.BigAttack:
                doBigAttackMove(false);
                break;
            case HeartMoves.Debuff:
                doDebuffMove(false);
                break;
            case HeartMoves.Summon:
                doSummonMove(false);
                break;
        }
        
        //this calls getMove
        addToBot(new RollMoveAction(this));
    }
    
    protected byte getLastMove()
    {
        if (this.moveHistory.isEmpty())
        {
            return 0;
        }
        else
        {
            return this.moveHistory.get(this.moveHistory.size() - 1);
        }
    }
    
    public void die()
    {
        if (!AbstractDungeon.getCurrRoom().cannotLose)
        {
            super.die();
            state.removeListener(animListener);
            onBossVictoryLogic();
            onFinalBossVictoryLogic();
            CardCrawlGame.stopClock = true;
        }
    }
    //endregion
}
