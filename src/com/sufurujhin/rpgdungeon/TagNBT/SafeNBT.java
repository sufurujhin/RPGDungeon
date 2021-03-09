package com.sufurujhin.rpgdungeon.TagNBT;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import io.netty.util.internal.UnstableApi;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;


import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SafeNBT{

 private static final String version = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
 private static final String cbVersion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
 private static Class<?> tagCompoundClass;
 private static Class<?> nbtBaseClass;
 private static Class<?> nmsItemstackClass;
 private static Class<?> craftItemstackClass;

 private final Object tagCompund;

 static{
     try{
         tagCompoundClass = Class.forName(version + ".NBTTagCompound");
         nbtBaseClass = Class.forName(version + ".NBTBase");
         nmsItemstackClass = Class.forName(version + ".ItemStack");
         craftItemstackClass = Class.forName(cbVersion + ".inventory.CraftItemStack");
     }
     catch(Exception ex){
         ex.printStackTrace();
         Bukkit.getPluginManager().disablePlugin(RPGDungeon.load);
     }
 }

 public SafeNBT(){
     this(null);
 }

 public SafeNBT(Object tagCompound){
     Object toSet = tagCompound;
     if(tagCompound == null){
         try{
             toSet = tagCompoundClass.newInstance();
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
     }
     this.tagCompund = toSet;
 }

 @UnstableApi
 public Object getTagCompund(){
     return tagCompund;
 }

 @Nullable
 public SafeNBT getCompoundNullable(String key){
     try{
         return getCompoundThrows(key);
     }
     catch(Exception ex){
         ex.printStackTrace();
         return null;
     }
 }

 public SafeNBT getCompoundThrows(String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
     Method m = tagCompoundClass.getMethod("getCompound", String.class);
     m.setAccessible(true);
     Object r = m.invoke(this.tagCompund, key);
     m.setAccessible(false);
     return r == null ? null : new SafeNBT(r);
 }

 public SafeNBT getCompound(String key){
     SafeNBT nbt = getCompoundNullable(key);
     return nbt == null ? null : new SafeNBT();
 }

 public SafeNBTList getListNullable(String key){
     try{
         return getListThrows(key);
     }
     catch(Exception ex){
         ex.printStackTrace();
         return null;
     }
 }

 public SafeNBTList getListThrows(String key)  throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
     Method m = tagCompoundClass.getMethod("get", String.class);
     m.setAccessible(true);
     Object r = m.invoke(this.tagCompund, key);
     m.setAccessible(false);
     return r == null ? null : new SafeNBTList(r);
 }

 public void setObject(String key, Object o){
     if(o instanceof String){setString(key, (String) o);}
     else if(o instanceof Integer){setInt(key, (Integer) o);}
     else if(o instanceof Double){setDouble(key, (Double) o);}
     else if(o instanceof Long){setLong(key, (Long) o);}
     else if(o instanceof List){
         SafeNBTList list = new SafeNBTList();
         for(Object e : (List) o){
             if(e instanceof Map){
                 SafeNBT mapNBT = new SafeNBT();
                 for(Object k : ((Map) e).keySet()){
                     if(k instanceof String){
                         Object v = ((Map) e).get(k);
                         mapNBT.setObject((String) k, v);
                     }
                 }
                 list.add(mapNBT);
             }
             else{
                 list.addGeneric(e);
             }
         }
         set(key, list);
     }
 }

 public SafeNBTList getList(String key){
     SafeNBTList nbt = getListNullable(key);
     return nbt == null ? null : new SafeNBTList();
 }

 public String getString(String key){
     try{
         Method m = tagCompoundClass.getMethod("getString", String.class);
         m.setAccessible(true);
         Object r = m.invoke(this.tagCompund, key);
         m.setAccessible(false);
         return r instanceof String ? (String) r : null;
     }
     catch(Exception ex){
         ex.printStackTrace();
         return null;
     }
 }

 public void setString(String key, String value){
     try{
         Method m = tagCompoundClass.getMethod("setString", String.class, String.class);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, value);
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public Integer getInt(String key){
     try{
         Method m = tagCompoundClass.getMethod("getInt", String.class);
         m.setAccessible(true);
         Object r = m.invoke(this.tagCompund, key);
         m.setAccessible(false);
         return r instanceof Integer ? (Integer) r : null;
     }
     catch(Exception ex){
         ex.printStackTrace();
         return null;
     }
 }

 public void setInt(String key, Integer value){
     try{
         Method m = tagCompoundClass.getMethod("setInt", String.class, int.class);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, value);
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public void setShort(String key, Short value){
     try{
         Method m = tagCompoundClass.getMethod("setShort", String.class, short.class);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, value);
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public void setBoolean(String key, Boolean value){
     try{
         Method m = tagCompoundClass.getMethod("setBoolean", String.class, boolean.class);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, value);
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public void setDouble(String key, Double value){
     try{
         Method m = tagCompoundClass.getMethod("setDouble", String.class, double.class);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, value);
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public Long getLong(String key){
     try{
         Method m = tagCompoundClass.getMethod("getLong", String.class);
         m.setAccessible(true);
         Object r = m.invoke(this.tagCompund, key);
         m.setAccessible(false);
         return r instanceof Long ? (Long) r : null;
     }
     catch(Exception ex){
         ex.printStackTrace();
         return null;
     }
 }

 public void setLong(String key, Long value){
     try{
         Method m = tagCompoundClass.getMethod("setLong", String.class, long.class);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, value);
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public void set(String key, SafeNBT value){
     try{
         Method m = tagCompoundClass.getMethod("set", String.class, nbtBaseClass);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, value.tagCompund);
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public void set(String key, SafeNBTList value){
     try{
         Method m = tagCompoundClass.getMethod("set", String.class, nbtBaseClass);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, value.getTagList());
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public void set(String key, SafeNBTBaseType type, Object value){
     try{
         Object toPut = type.make(value);
         Method m = tagCompoundClass.getMethod("set", String.class, nbtBaseClass);
         m.setAccessible(true);
         m.invoke(this.tagCompund, key, toPut);
         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }


 public void setStrings(Map<String, String> map){
     try{
         Method m = tagCompoundClass.getMethod("setString", String.class, String.class);
         m.setAccessible(true);
         map.forEach((String key, String value) -> {
             try{
                 m.invoke(this.tagCompund, key, value);
             }
             catch(Exception ex){ex.printStackTrace();}
         });

         m.setAccessible(false);
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
 }

 public boolean hasKey(String key){
     try{
         Method m = tagCompoundClass.getMethod("hasKey", String.class);
         m.setAccessible(true);
         Object o = m.invoke(this.tagCompund, key);
         m.setAccessible(false);

         return o instanceof Boolean && (Boolean) o;
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
     return false;
 }

 public ItemStack apply(ItemStack item){
     try{
         Method nmsGet = craftItemstackClass.getMethod("asNMSCopy", ItemStack.class);
         nmsGet.setAccessible(true);
         Object nmsStack = nmsGet.invoke(null, item);
         nmsGet.setAccessible(false);

         Method nbtSet = nmsItemstackClass.getMethod("setTag", tagCompoundClass);
         nbtSet.setAccessible(true);
         nbtSet.invoke(nmsStack, this.tagCompund);
         nbtSet.setAccessible(false);

         Method m = craftItemstackClass.getMethod("asBukkitCopy", nmsItemstackClass);
         m.setAccessible(true);
         Object o = m.invoke(null, nmsStack);
         m.setAccessible(false);

         return o instanceof ItemStack ? (ItemStack) o : null;
     }
     catch(Exception ex){
         ex.printStackTrace();
         return null;
     }
 }

 public static SafeNBT get(ItemStack item){
     try{
         Method m = craftItemstackClass.getMethod("asNMSCopy", ItemStack.class);
         m.setAccessible(true);
         Object nmsStack = m.invoke(null, item);
         m.setAccessible(false);

         Method getCompound = nmsItemstackClass.getMethod("getTag");
         getCompound.setAccessible(true);
         Object nbtCompound = getCompound.invoke(nmsStack);
         getCompound.setAccessible(false);

         return new SafeNBT(nbtCompound);
     }
     catch(Exception ex){
         ex.printStackTrace();
         return null;
     }
 }

 public String toString(){
     return "SafeNBT(" + compoundString() + ")";
 }

 public String compoundString(){return Objects.toString(tagCompund);}
}
