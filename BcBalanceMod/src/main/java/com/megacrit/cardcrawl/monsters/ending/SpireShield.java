package com.megacrit.cardcrawl.monsters.ending;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;

import java.util.Iterator;

public class SpireShield extends AbstractMonster
{
    public static final String ID = "SpireShield";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private int moveCount = 0;
    private static final byte BASH = 1;
    private static final byte FORTIFY = 2;
    private static final byte SMASH = 3;
    private static final int BASH_DEBUFF = -1;
    private static final int FORTIFY_BLOCK = 30;
    
    public SpireShield()
    {
        super(NAME, "SpireShield", 110, 0.0F, -20.0F, 380.0F, 290.0F, (String) null, -1000.0F, 15.0F);
        type = AbstractMonster.EnemyType.ELITE;
        loadAnimation("images/monsters/theEnding/shield/skeleton.atlas", "images/monsters/theEnding/shield/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        if (AbstractDungeon.ascensionLevel >= 8)
        {
            //setHp(125);
            setHp(140);
        }
        else
        {
            setHp(110);
        }
        
        if (AbstractDungeon.ascensionLevel >= 3)
        {
            damage.add(new DamageInfo(this, 14));
            damage.add(new DamageInfo(this, 38));
        }
        else
        {
            damage.add(new DamageInfo(this, 12));
            damage.add(new DamageInfo(this, 34));
        }
        
    }
    
    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SurroundedPower(AbstractDungeon.player)));
        if (AbstractDungeon.ascensionLevel >= 18)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1)));
        }
        
    }
    
    public void takeTurn()
    {
label27:
        switch (nextMove)
        {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.35F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                if (!AbstractDungeon.player.orbs.isEmpty() && AbstractDungeon.aiRng.randomBoolean())
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -1), -1));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
                }
                break;
            case 2:
                Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
                
                while (true)
                {
                    if (!var1.hasNext())
                    {
                        break label27;
                    }
                    
                    AbstractMonster m = (AbstractMonster) var1.next();
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, 30));
                }
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OLD_ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                if (AbstractDungeon.ascensionLevel >= 18)
                {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 99));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, ((DamageInfo) damage.get(1)).output));
                }
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    protected void getMove(int num)
    {
        switch (moveCount % 3)
        {
            case 0:
                if (AbstractDungeon.aiRng.randomBoolean())
                {
                    setMove((byte) 2, AbstractMonster.Intent.DEFEND);
                }
                else
                {
                    setMove((byte) 1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(0)).base);
                }
                break;
            case 1:
                if (!lastMove((byte) 1))
                {
                    setMove((byte) 1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(0)).base);
                }
                else
                {
                    setMove((byte) 2, AbstractMonster.Intent.DEFEND);
                }
                break;
            default:
                setMove((byte) 3, AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo) damage.get(1)).base);
        }
        
        ++moveCount;
    }
    
    public void changeState(String key)
    {
        byte var3 = -1;
        switch (key.hashCode())
        {
            case 334276480:
                if (key.equals("OLD_ATTACK"))
                {
                    var3 = 0;
                }
                break;
            case 1941037640:
                if (key.equals("ATTACK"))
                {
                    var3 = 1;
                }
        }
        
        switch (var3)
        {
            case 0:
                state.setAnimation(0, (String) "old_attack", false);
                state.addAnimation(0, (String) "Idle", true, 0.0F);
                break;
            case 1:
                state.setAnimation(0, (String) "Attack", false);
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
    
    public void die()
    {
        super.die();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        
        while (var1.hasNext())
        {
            AbstractMonster m = (AbstractMonster) var1.next();
            if (!m.isDead && !m.isDying)
            {
                if (AbstractDungeon.player.hasPower("Surrounded"))
                {
                    AbstractDungeon.player.flipHorizontal = m.drawX < AbstractDungeon.player.drawX;
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Surrounded"));
                }
                
                if (m.hasPower("BackAttack"))
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, "BackAttack"));
                }
            }
        }
        
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpireShield");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
