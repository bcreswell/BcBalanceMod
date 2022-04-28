package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ModeShiftPower;
import com.megacrit.cardcrawl.powers.SharpHidePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheGuardian extends AbstractMonster
{
    private static final Logger logger = LogManager.getLogger(TheGuardian.class.getName());
    public static final String ID = "TheGuardian";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final String DEFENSIVE_MODE = "Defensive Mode";
    private static final String OFFENSIVE_MODE = "Offensive Mode";
    private static final String RESET_THRESH = "Reset Threshold";
    public static final int HP = 240;
    public static final int A_2_HP = 250;
    private static final int DMG_THRESHOLD = 30;
    private static final int A_2_DMG_THRESHOLD = 35;
    private static final int A_19_DMG_THRESHOLD = 40;
    private int dmgThreshold;
    private int dmgThresholdIncrease = 10;
    private int dmgTaken;
    private static final int FIERCE_BASH_DMG = 32;
    private static final int A_2_FIERCE_BASH_DMG = 36;
    private static final int ROLL_DMG = 9;
    private static final int A_2_ROLL_DMG = 10;
    private int fierceBashDamage;
    private int whirlwindDamage = 5;
    private int twinSlamDamage = 8;
    private int rollDamage;
    private int whirlwindCount = 4;
    private int DEFENSIVE_BLOCK = 20;
    private int fierceBashBlockAmount;
    private int thornsDamage = 3;
    private int VENT_DEBUFF = 2;
    private boolean isOpen = true;
    private boolean closeUpTriggered = false;
    private static final byte CLOSE_UP = 1;
    private static final byte FIERCE_BASH = 2;
    private static final byte ROLL_ATTACK = 3;
    private static final byte TWIN_SLAM = 4;
    private static final byte WHIRLWIND = 5;
    private static final byte CHARGE_UP = 6;
    private static final byte VENT_STEAM = 7;
    private static final String CLOSEUP_NAME;
    private static final String FIERCEBASH_NAME;
    private static final String TWINSLAM_NAME;
    private static final String WHIRLWIND_NAME;
    private static final String CHARGEUP_NAME;
    private static final String VENTSTEAM_NAME;
    
    public TheGuardian()
    {
        super(NAME, "TheGuardian", 240, 0.0F, 95.0F, 440.0F, 350.0F, (String) null, -50.0F, -100.0F);
        type = AbstractMonster.EnemyType.BOSS;
        dialogX = -100.0F * Settings.scale;
        dialogY = 50.0F * Settings.scale;
        
        setHp(240);
        fierceBashBlockAmount = 9;
        fierceBashDamage = 32;
        rollDamage = 9;
        dmgThreshold = 30;
        
        //bosses are deadlier
        if (AbstractDungeon.ascensionLevel >= 4)
        {
            fierceBashDamage = 36;
            rollDamage = 10;
        }
        
        //bosses have more HP
        if (AbstractDungeon.ascensionLevel >= 9)
        {
            //setHp(250);
            setHp(280);
            dmgThreshold = 35;
        }
        
        //bosses have more challenging movesets
        if (AbstractDungeon.ascensionLevel >= 19)
        {
            fierceBashBlockAmount = 15;
            dmgThreshold = 40;
        }
        
        damage.add(new DamageInfo(this, fierceBashDamage));
        damage.add(new DamageInfo(this, rollDamage));
        damage.add(new DamageInfo(this, whirlwindDamage));
        damage.add(new DamageInfo(this, twinSlamDamage));
        loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 2.0F);
        state.setAnimation(0, (String) "idle", true);
    }
    
    public void usePreBattleAction()
    {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
        {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        }
        
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, dmgThreshold)));
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));
        UnlockTracker.markBossAsSeen("GUARDIAN");
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case 1:
                useCloseUp();
                break;
            case 2:
                useFierceBash();
                break;
            case 3:
                useRollAttack();
                break;
            case 4:
                useTwinSmash();
                break;
            case 5:
                useWhirlwind();
                break;
            case 6:
                useChargeUp();
                break;
            case 7:
                useVentSteam();
                break;
            default:
                logger.info("ERROR");
        }
        
    }
    
    private void useFierceBash()
    {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        setMove(VENTSTEAM_NAME, (byte) 7, AbstractMonster.Intent.STRONG_DEBUFF);
    }
    
    private void useVentSteam()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, VENT_DEBUFF, true), VENT_DEBUFF));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, VENT_DEBUFF, true), VENT_DEBUFF));
        setMove(WHIRLWIND_NAME, (byte) 5, AbstractMonster.Intent.ATTACK, whirlwindDamage, whirlwindCount, true);
    }
    
    private void useCloseUp()
    {
        AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, DIALOG[1]));
        if (AbstractDungeon.ascensionLevel >= 19)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, thornsDamage + 1)));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, thornsDamage)));
        }
        
        setMove((byte) 3, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(1)).base);
    }
    
    private void useTwinSmash()
    {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Offensive Mode"));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(3), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(3), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Sharp Hide"));
        setMove(WHIRLWIND_NAME, (byte) 5, AbstractMonster.Intent.ATTACK, whirlwindDamage, whirlwindCount, true);
    }
    
    private void useRollAttack()
    {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        setMove(TWINSLAM_NAME, (byte) 4, AbstractMonster.Intent.ATTACK_BUFF, twinSlamDamage, 2, true);
    }
    
    private void useWhirlwind()
    {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
        
        for (int i = 0; i < whirlwindCount; ++i)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(2), AbstractGameAction.AttackEffect.NONE, true));
        }
        
        setMove(CHARGEUP_NAME, (byte) 6, AbstractMonster.Intent.DEFEND);
    }
    
    private void useChargeUp()
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, fierceBashBlockAmount));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 1.0F, 2.5F));
        setMove(FIERCEBASH_NAME, (byte) 2, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(0)).base);
    }
    
    protected void getMove(int num)
    {
        if (isOpen)
        {
            setMove(CHARGEUP_NAME, (byte) 6, AbstractMonster.Intent.DEFEND);
        }
        else
        {
            setMove((byte) 3, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(1)).base);
        }
        
    }
    
    public void changeState(String stateName)
    {
        byte var3 = -1;
        switch (stateName.hashCode())
        {
            case -927957434:
                if (stateName.equals("Offensive Mode"))
                {
                    var3 = 1;
                }
                break;
            case 631623152:
                if (stateName.equals("Defensive Mode"))
                {
                    var3 = 0;
                }
                break;
            case 786294426:
                if (stateName.equals("Reset Threshold"))
                {
                    var3 = 2;
                }
        }
        
        switch (var3)
        {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Mode Shift"));
                CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, DEFENSIVE_BLOCK));
                stateData.setMix("idle", "transition", 0.1F);
                state.setTimeScale(2.0F);
                state.setAnimation(0, (String) "transition", false);
                state.addAnimation(0, (String) "defensive", true, 0.0F);
                dmgThreshold += dmgThresholdIncrease;
                setMove(CLOSEUP_NAME, (byte) 1, AbstractMonster.Intent.BUFF);
                createIntent();
                isOpen = false;
                updateHitbox(0.0F, 95.0F, 440.0F, 250.0F);
                healthBarUpdatedEvent();
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, dmgThreshold)));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));
                if (currentBlock != 0)
                {
                    AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, currentBlock));
                }
                
                stateData.setMix("defensive", "idle", 0.2F);
                state.setTimeScale(1.0F);
                state.setAnimation(0, (String) "idle", true);
                isOpen = true;
                closeUpTriggered = false;
                updateHitbox(0.0F, 95.0F, 440.0F, 350.0F);
                healthBarUpdatedEvent();
                break;
            case 2:
                dmgTaken = 0;
        }
        
    }
    
    public void damage(DamageInfo info)
    {
        int tmpHealth = currentHealth;
        super.damage(info);
        if (isOpen && !closeUpTriggered && tmpHealth > currentHealth && !isDying)
        {
            dmgTaken += tmpHealth - currentHealth;
            if (getPower("Mode Shift") != null)
            {
                AbstractPower var10000 = getPower("Mode Shift");
                var10000.amount -= tmpHealth - currentHealth;
                getPower("Mode Shift").updateDescription();
            }
            
            if (dmgTaken >= dmgThreshold)
            {
                dmgTaken = 0;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(hb.cX, hb.cY, false), 0.05F, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Defensive Mode"));
                closeUpTriggered = true;
            }
        }
        
    }
    
    public void render(SpriteBatch sb)
    {
        super.render(sb);
    }
    
    public void die()
    {
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        onBossVictoryLogic();
        UnlockTracker.hardUnlockOverride("GUARDIAN");
        UnlockTracker.unlockAchievement("GUARDIAN");
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TheGuardian");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        CLOSEUP_NAME = MOVES[0];
        FIERCEBASH_NAME = MOVES[1];
        TWINSLAM_NAME = MOVES[3];
        WHIRLWIND_NAME = MOVES[4];
        CHARGEUP_NAME = MOVES[5];
        VENTSTEAM_NAME = MOVES[6];
    }
}
