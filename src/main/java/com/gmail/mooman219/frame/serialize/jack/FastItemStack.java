package com.gmail.mooman219.frame.serialize.jack;

import java.lang.ref.WeakReference;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JacksonData;

public class FastItemStack implements JacksonData {
    private static final long serialVersionUID = 5223854976659448343L;
    // Default
    private int id;
    private int amount;
    private short damage;
    private byte data;
    // ItemMeta
    private String displayName;
    private List<String> lore;
    // BookMeta
    private String author;
    private String title;
    private List<String> pages;
    // LeatherArmorMeta
    private FastColor armorColor;
    // SkullMeta
    private String owner;

    private transient WeakReference<ItemStack> weakItemStack;

    protected FastItemStack() {}

    public FastItemStack(ItemStack itemstack) {
        id = itemstack.getTypeId();
        damage = itemstack.getDurability();
        amount = itemstack.getAmount();
        data = itemstack.getData().getData();
        if(itemstack.getItemMeta() != null) {
            ItemMeta itemMeta = itemstack.getItemMeta();
            if(itemMeta.hasDisplayName()) {
                displayName = itemMeta.getDisplayName();
            }
            if(itemMeta.hasLore()) {
                lore = itemMeta.getLore();
            }
            if(itemMeta instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta) itemMeta;
                if(bookMeta.hasAuthor()) {
                    author = bookMeta.getAuthor();
                }
                if(bookMeta.hasTitle()) {
                    title = bookMeta.getTitle();
                }
                if(bookMeta.hasPages()) {
                    pages = bookMeta.getPages();
                }
            } else if(itemMeta instanceof LeatherArmorMeta) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                // getColor is never null
                armorColor = new FastColor(leatherArmorMeta.getColor());
            } else if(itemMeta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                if(skullMeta.hasOwner()) {
                    this.owner = skullMeta.getOwner();
                }
            }
        }
    }

    public ItemStack toItemStack() {
        if(weakItemStack == null || weakItemStack.get() == null) {
            ItemStack itemstack = new ItemStack(id, amount, damage);
            itemstack.getData().setData(data);
            if(itemstack.getItemMeta() != null) {
                ItemMeta itemMeta = itemstack.getItemMeta();
                if(displayName != null) {
                    itemMeta.setDisplayName(displayName);
                }
                if(lore != null) {
                    itemMeta.setLore(lore);
                }
                if(itemMeta instanceof BookMeta) {
                    BookMeta bookMeta = (BookMeta) itemMeta;
                    if(author != null) {
                        bookMeta.setAuthor(author);
                    }
                    if(title != null) {
                        bookMeta.setTitle(title);
                    }
                    if(pages != null) {
                        bookMeta.setPages(pages);
                    }
                } else if(itemMeta instanceof LeatherArmorMeta) {
                    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                    if(armorColor != null) {
                        leatherArmorMeta.setColor(armorColor.toColor());
                    }
                } else if(itemMeta instanceof SkullMeta) {
                    SkullMeta skullMeta = (SkullMeta) itemMeta;
                    if(owner != null) {
                        skullMeta.setOwner(owner);
                    }
                }
                itemstack.setItemMeta(itemMeta);
            }
            weakItemStack = new WeakReference<ItemStack>(itemstack);
        }
        return weakItemStack.get();
    }

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastItemStack deserialize(String data) {
        return JsonHelper.fromJackson(data, FastItemStack.class);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result
                + ((armorColor == null) ? 0 : armorColor.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + damage;
        result = prime * result + data;
        result = prime * result
                + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + id;
        result = prime * result + ((lore == null) ? 0 : lore.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((pages == null) ? 0 : pages.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FastItemStack other = (FastItemStack) obj;
        if (amount != other.amount) {
            return false;
        }
        if (armorColor == null) {
            if (other.armorColor != null) {
                return false;
            }
        } else if (!armorColor.equals(other.armorColor)) {
            return false;
        }
        if (author == null) {
            if (other.author != null) {
                return false;
            }
        } else if (!author.equals(other.author)) {
            return false;
        }
        if (damage != other.damage) {
            return false;
        }
        if (data != other.data) {
            return false;
        }
        if (displayName == null) {
            if (other.displayName != null) {
                return false;
            }
        } else if (!displayName.equals(other.displayName)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (lore == null) {
            if (other.lore != null) {
                return false;
            }
        } else if (!lore.equals(other.lore)) {
            return false;
        }
        if (owner == null) {
            if (other.owner != null) {
                return false;
            }
        } else if (!owner.equals(other.owner)) {
            return false;
        }
        if (pages == null) {
            if (other.pages != null) {
                return false;
            }
        } else if (!pages.equals(other.pages)) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }
}
