package by.kislyakoff.HomeBudgetApp.util.helpers;

import java.util.ArrayList;
import java.util.List;

public class PaginationHelper {
	
	// константа количества отображаемых страниц до разрыва
	    private static final int SKIP_VISIBLE = 3;

	    // ячейка в пажинаторе
	    public static class Item {
	        public final int     number;
	        public final boolean isActive;
	        public final boolean isSkipped;

	        // конструктор #1
	        Item(final int number, final boolean isActive) {
	            this.number    = number;
	            this.isActive  = isActive;
	            this.isSkipped = false;
	        }

	        // конструктор #2
	        Item() {
	            this.number    = -1;
	            this.isActive  = false;
	            this.isSkipped = true;
	        }
	    }

	    // список ячеек
	    public final Item[] pages;

	    // закрытый конструктор
	    private PaginationHelper(final Item[] pages) {
	        this.pages = pages;
	    }
	    
	 // создать пажинатор по заданным параметрам
	    public static PaginationHelper generate(final int currentPage, final int totalPages) {
	    	
	    	if (currentPage <= 0 || currentPage > totalPages || totalPages < 2) {
	            return null;
	        }
	    	
	    	final List<Item> pages = new ArrayList<>();
	    	
	    	// проверяем нужны ли отступы относительно заданного SKIP_VISIBLE
	    	final boolean skipLeft = (SKIP_VISIBLE + 1) < (currentPage - 1);
	    	final boolean skipRight = (totalPages - SKIP_VISIBLE) > (currentPage + 1);
	    	final boolean skipRatio = (double) totalPages / 2 > SKIP_VISIBLE;
	    	int number;
	    	
	    	
	    	if (!skipLeft && !skipRight || !skipRatio) { // если отступов нет
	    		for(number = 1; number <= totalPages; number++) {
	    			pages.add(new Item(number, number == currentPage));
	    		}
	    	} else if (!skipLeft && skipRight) { // отступ справа от текущей страницы
	    		for(number = 1; number <= currentPage + 1 || number <= SKIP_VISIBLE; number++) {
	    			pages.add(new Item(number, number == currentPage));
	    		}
	    		pages.add(new Item());
	    		for(number = totalPages - SKIP_VISIBLE + 1; number <= totalPages; number++) {
	    			pages.add(new Item(number, false));
	    		}
	    	} else if (skipLeft && !skipRight) { // отступ слева от текущей страницы
	    		for (number = 1; number <= SKIP_VISIBLE; number++) {
	                pages.add(new Item(number, false));
	            }
	            pages.add(new Item());
	            for (number = Math.min(currentPage - 1, totalPages - SKIP_VISIBLE + 1); number <= totalPages; number++) {
	                pages.add(new Item(number, number == currentPage));
	            }
	    	} else { // отступ слева и справа
	    		for (number = 1; number <= SKIP_VISIBLE; number++) {
	                pages.add(new Item(number, false));
	            }
	            pages.add(new Item());
	            for (number = currentPage - 1; number <= currentPage + 1; number++) {
	                pages.add(new Item(number, number == currentPage));
	            }
	            pages.add(new Item());
	            for (number = totalPages - SKIP_VISIBLE + 1; number <= totalPages; number++) {
	                pages.add(new Item(number, false));
	            }
	    	}
	    	return new PaginationHelper(pages.toArray(new Item[pages.size()]));
	    }

}
