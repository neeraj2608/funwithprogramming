.PHONY: makedirs all test clean cl

GCC = gcc
GCCFLAGS = -w
CLASSDIR = obj
SRCDIR = .

.SUFFIXES: .c .o

# -----------------
#  Common targets
# -----------------
all: makedirs source

makedirs:
	@mkdir -p $(CLASSDIR)

cl: clean

clean:
	@rm -rf $(CLASSDIR)

# -----------------
# Source Files
# -----------------
SOURCEFILES := \
				StrManip.c \

$(CLASSDIR)/%.o : $(SRCDIR)/%.c
	@echo ""
	@echo "--------------------------------------------------------------------"
	@echo "Compiling source file:\t" $<
	@echo "--------------------------------------------------------------------"
	@$(GCC) $(GCCFLAGS) -o $@ $<

SOURCEFILESTOCOMPILE = $(addprefix $(CLASSDIR)/, $(SOURCEFILES:.c=.o))

source: makedirs $(SOURCEFILESTOCOMPILE)
