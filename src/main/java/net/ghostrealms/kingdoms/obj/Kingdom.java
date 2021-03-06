package net.ghostrealms.kingdoms.obj;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.exception.NegativeMoneyException;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;

public class Kingdom {
    
    private static final String ECON_PREFIX = "kingdom_";
    
    private int id;
    private String name = "";
    private List<String> towns = new ArrayList<String>();
    private Date creationDate;
    private List<Request> requests = new ArrayList<Request>();
    
    private char[] outputColor = { '6', 'e' };
    
    public Kingdom(int id, String name, List<String> towns, Date creationDate) {
        this.setAllFields(id, name, towns, creationDate);
    }
    
    public void setAllFields(int id, String name, List<String> towns, Date creationDate) {
        this.id = id;
        this.name = name;
        this.towns = towns;
        this.creationDate = creationDate;
        initTowns();
        initRequests();
    }
    
    public void initTowns() {
        KingdomsManager.loadAllTowns(this);
    }
    
    public void initRequests() {
        KingdomsManager.loadOpenRequests(this);
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public Town getTown(String name) {
        for (String s : this.towns) {
            Town town = KingdomsManager.getTown(s);
            if (town.getName().equalsIgnoreCase(name)) { return town; }
        }
        return null;
    }
    
    public List<Town> getTowns() {
        List<Town> tmp = new ArrayList<Town>();
        for (String s : towns) {
            tmp.add(KingdomsManager.getTown(s));
        }
        return tmp;
    }
    
    public List<String> getTownNames() {
        return this.towns;
    }
    
    public Town getCapital() {
        for (String s : this.towns) {
            Town town = KingdomsManager.getTown(s);
            if (town.isCapital()) { return town; }
        }
        return null;
    }
    
    public Resident getKing() {
        return this.getCapital().getMayor();
    }
    
    public List<Resident> getCouncil() {
        List<Resident> tmp = new ArrayList<Resident>();
        for (String s : this.towns) {
            Town town = KingdomsManager.getTown(s);
            if (!town.isCapital()) {
                tmp.add(town.getMayor());
            } else {
            	for (Resident assistant : town.getAssistants()) {
            		tmp.add(assistant);
            	}
            }
        }
        return tmp;
    }
    
    public List<String> getCouncilNames() {
        List<String> tmp = new ArrayList<String>();
        for (Resident r : getCouncil()) {
            if (r == null) {
                continue;
            }
            tmp.add(r.getName());
        }
        return tmp;
    }
    
    public List<String> getAllResidents() {
        List<String> residents = new ArrayList<String>();
        for (String s : this.towns) {
            Town town = KingdomsManager.getTown(s);
            residents.addAll(town.getResidentsNames());
        }
        return residents;
    }
    
    public List<String> getOnlineResidents() {
        List<String> residents = new ArrayList<String>();
        for (String s : this.towns) {
            Town town = KingdomsManager.getTown(s);
            residents.addAll(town.getResidentsNames());
        }
        return residents;
    }
    
    public String getEconName() {
        return ECON_PREFIX + getName();
    }
    
    public double getBalance() {
        return DeityAPI.getAPI().getEconAPI().getBalance(getEconName());
    }
    
    public String getFormattedBalance() {
        return DeityAPI.getAPI().getEconAPI().getFormattedBalance(getEconName());
    }
    
    public List<Request> getRequests() {
        List<Request> openRequests = new ArrayList<Request>();
        List<Request> closedRequests = new ArrayList<Request>();
        for (Request r : requests) {
            if (!r.isClosed()) {
                openRequests.add(r);
            } else {
                closedRequests.add(r);
            }
        }
        List<Request> requests = new ArrayList<Request>();
        requests.addAll(openRequests);
        requests.addAll(closedRequests);
        return requests;
    }
    
    public Request getRequest(int requestId) {
        for (Request r : requests) {
            if (r.getId() == requestId) { return r; }
        }
        return null;
    }
    
    public void setTowns(List<String> towns) {
        this.towns = towns;
    }
    
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
    
    public void addTown(Town town, boolean isCapital) {
        this.towns.add(town.getName());
        
        if (isCapital) {
            town.setCapital(true);
            town.getMayor().setKing(true);
            town.getMayor().save();
        }
        town.setKingdom(this);
        town.save();
        KingdomsMain.plugin.chat
                .sendGlobalMessage(String.format(KingdomsMessageHelper.CMD_KINGDOM_TOWN_ADD, town.getName(), getName()));
    }
    
    public void addRequest(Request request) {
        this.requests.add(request);
        if (getKing().isOnline()) {
            KingdomsMain.plugin.chat.sendPlayerMessage(this.getKing().getPlayer(), KingdomsMessageHelper.CMD_REQUEST_KIGDOM_NEW);
        }
    }
    
    public void removeTown(Town town) {
        this.towns.remove(town);
        
        town.setKingdom(null);
        town.save();
    }
    
    public void removeRequest(Request request) {
        request.setClosed(true);
        request.save();
        this.requests.remove(request);
    }
    
    public boolean hasResident(String resident) {
        return getAllResidents().contains(resident);
    }
    
    public boolean hasStaff(String resident) {
        if (getKing().getName().equalsIgnoreCase(resident)) { return true; }
        return getCouncilNames().contains(resident);
    }
    
    public void createBankAccount() {
        DeityAPI.getAPI().getEconAPI().createAccount(getEconName());
    }
    
    public boolean canPay(double cost) {
        return DeityAPI.getAPI().getEconAPI().canPay(getEconName(), cost);
    }
    
    public void pay(double cost, String note) {
        try {
            DeityAPI.getAPI().getEconAPI().send(getEconName(), cost, note);
        } catch (NegativeMoneyException e) {
        }
    }
    
    public void pay(String receiver, double cost, String note) {
        try {
            DeityAPI.getAPI().getEconAPI().sendTo(getEconName(), receiver, cost, note);
        } catch (NegativeMoneyException e) {
        }
    }
    
    public void sendMessage(String message) {
        for (Town town : this.getTowns()) {
            town.sendMessage(message);
        }
    }
    
    public void sendMessageNoHeader(String message) {
        for (Town town : this.getTowns()) {
            town.sendMessageNoHeader(message);
        }
    }
    
    public List<String> showInfo() {
        List<String> out = new ArrayList<String>();
        out.add("&" + outputColor[0] + "+-----------------------------+");
        out.add("&" + outputColor[0] + "Kingdom: &" + outputColor[1] + this.getName() + " &8[" + this.getId() + "]");
        if (this.getKing() != null) {
            out.add("&" + outputColor[0] + this.getKing().getKingdomFriendlyTitle() + ": &" + outputColor[1]
                    + this.getKing().getName());
        }
        if (this.getCouncilNames() != null && this.getCouncilNames().size() > 0) {
            out.add("&" + outputColor[0] + "Council: &" + outputColor[1]
                    + DeityAPI.getAPI().getUtilAPI().getStringUtils().join(this.getCouncilNames(), ", "));
        }
        out.add("&"
                + outputColor[0]
                + "Creation Date: &"
                + outputColor[1]
                + (this.getCreationDate() == null ? "Right Now" : DeityAPI.getAPI().getUtilAPI().getTimeUtils()
                        .getFriendlyDate(this.getCreationDate(), false)));
        out.add("&" + outputColor[0] + "Balance: &" + outputColor[1] + this.getFormattedBalance());
        if (this.getTownNames() != null && !this.getAllResidents().isEmpty()) {
            String list = "&" + outputColor[1]
                    + DeityAPI.getAPI().getUtilAPI().getStringUtils().join(this.getTownNames(), "&7, &" + outputColor[1]);
            out.add("&" + outputColor[0] + "Towns &" + outputColor[1] + "[" + this.getTownNames().size() + "] &f: " + list);
        }
        return out;
    }
}
