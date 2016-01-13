package thehambone.gtatools.gta3savefileeditor.newshit.struct;

import thehambone.gtatools.gta3savefileeditor.newshit.DataBuffer;
import thehambone.gtatools.gta3savefileeditor.newshit.SaveFileNew;
import thehambone.gtatools.gta3savefileeditor.newshit.UnsupportedPlatformException;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.VarByte;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.VarFloat;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.VarInt;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.VarShort;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.VarString;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.VarString16;

/**
 * Created on Sep 21, 2015.
 * 
 * @author thehambone
 */
public class BlockSimpleVars extends Block
{
    public final VarString szSaveName = new VarString16(24);
    public final Timestamp timestamp = new Timestamp();
    public final VarInt nCurrentLevel = new VarInt();
    public final RWv3D vCameraPosition = new RWv3D();
    public final VarInt nGameMinuteLengthMillis = new VarInt();
    public final VarInt nLastClockTick = new VarInt();
    public final VarByte nGameHours = new VarByte();
    public final VarByte nGameMinutes = new VarByte();
    public final VarInt nTimeInMillis = new VarInt();
    public final VarInt nFrameCounter = new VarInt();
    public final VarShort nPreviousWeatherType = new VarShort();
    public final VarShort nCurrentWeatherType = new VarShort();
    public final VarShort nForcedWeatherType = new VarShort();
    public final VarFloat fWeatherInterpolationValue = new VarFloat();
    
    public final BlockTheScripts theScripts = new BlockTheScripts(0x00);
    
    public BlockSimpleVars(int size)
    {
        super("SimpleVars", size);
    }
    
    @Override
    public void setSize(int size)
    {
        // TODO: implement
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public DataStructure[] getMembers()
    {
        return new DataStructure[] {
            szSaveName, timestamp, nCurrentLevel, vCameraPosition,
            nGameMinuteLengthMillis, nLastClockTick, nGameHours,
            nGameMinutes, nTimeInMillis, nFrameCounter,
            nPreviousWeatherType, nCurrentWeatherType, nForcedWeatherType,
            fWeatherInterpolationValue, theScripts
        };
    }
    
    @Override
    protected void loadAndroid(DataBuffer buf)
    {
        throw new UnsupportedPlatformException("Android not supported yet.");
    }
    
    @Override
    protected void loadIOS(DataBuffer buf)
    {
        throw new UnsupportedPlatformException("iOS not supported yet.");
    }
    
    @Override
    protected void loadPC(DataBuffer buf)
    {
        szSaveName.load(buf, offset + 0x00);
        timestamp.load(buf, offset + 0x30, SaveFileNew.Platform.PC);
        nCurrentLevel.load(buf, offset + 0x44);
        vCameraPosition.load(buf, offset + 0x48, SaveFileNew.Platform.PC);
        nGameMinuteLengthMillis.load(buf, offset + 0x54);
        nLastClockTick.load(buf, offset + 0x58);
        nGameHours.load(buf, offset + 0x5C);
        nGameMinutes.load(buf, offset + 0x60);
        nTimeInMillis.load(buf, offset + 0x68);
        nFrameCounter.load(buf, offset + 0x78);
        nPreviousWeatherType.load(buf, offset + 0x88);
        nCurrentWeatherType.load(buf, offset + 0x8C);
        nForcedWeatherType.load(buf, offset + 0x90);
        fWeatherInterpolationValue.load(buf, offset + 0x94);
        
        buf.seek(offset + 0xBC);
        theScripts.setSize(buf.readInt());
        theScripts.load(buf, offset + 0xC0, SaveFileNew.Platform.PC);
    }
    
    @Override
    protected void loadPS2(DataBuffer buf)
    {
        throw new UnsupportedPlatformException("PS2 not supported yet.");
    }
    
    @Override
    protected void loadXbox(DataBuffer buf)
    {
        throw new UnsupportedPlatformException("Xbox not supported yet.");
    }
}
